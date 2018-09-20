package cn.seckill.service.impl;

import cn.seckill.dao.SeckillDao;
import cn.seckill.dao.SuccessKilledDao;
import cn.seckill.dao.cache.RedisDao;
import cn.seckill.dto.Exposer;
import cn.seckill.dto.SeckillExecution;
import cn.seckill.entity.Seckill;
import cn.seckill.entity.SuccessKilled;
import cn.seckill.enums.SeckillStatEnum;
import cn.seckill.exception.RepeatKillException;
import cn.seckill.exception.SeckillCloseException;
import cn.seckill.exception.SeckillException;
import cn.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/21 0021.
 */
@Service(value = "seckillServiceImpl")
public class SeckillServiceImpl  implements SeckillService{

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    //盐值
    private final String slat="dasuklsd151fsa51f..;";

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

    /**
     * 查询所有秒杀记录
     */
    public List<Seckill> getAllSeckill() {
        List<Seckill> seckillList=seckillDao.queryAll(0,100);
        return seckillList;
    }

    /**
     * 根据id查询秒杀记录
     *
     * @param seckillId
     */
    public Seckill querySeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 秒杀开启时输出秒杀地址，否则输出秒杀时间和系统时间
     *
     * @param seckillId
     */
    public Exposer exportSeckillUrl(long seckillId) {
        //优化点：缓存优化，在超时的基础上维护一致性
        //1.访问redis
        Seckill seckill=redisDao.getSeckill(seckillId);
        if(seckill==null){
            //2.访问数据库
            seckill=seckillDao.queryById(seckillId);
            if(seckill==null){
                return new Exposer(false,seckillId);
            }else {
                //3.写入redis
                redisDao.putSeckill(seckill);
            }
        }
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date nowTime=new Date();
        if(nowTime.getTime()>endTime.getTime()||nowTime.getTime()<startTime.getTime()){
            logger.info("秒杀未开启：nowTime:{},endTime:{}",nowTime,endTime);
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转换特定字符串过程，不可逆
        String md5=getMD5(seckillId) ;
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+slat;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());//spring自带的MD5生成工具
        return md5;
    }

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */

    /**
     * 使用注解控制事务方法的优点：未执行完所有数据库操作，事务回滚
     * 1、开发团队达成一致明确标注事务方法的编程风格
     * 2、保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外
     * 3、不是所有的方法都需要事务，只有一条修改操作或者只读操作不需要事务控制
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException {
            if(md5==null||!(md5.equals(getMD5(seckillId)))){
                //数据被篡改
                throw new SeckillException("Seckill data rewrite");
            }
            //执行秒杀逻辑：减库存，记录购买行为
            Date nowTime=new Date();
            try {
                //记录购买行为
                int insertCount=successKilledDao.insertSuccessKilled(seckillId,userPhone);
                if(insertCount<=0){
                    //重复秒杀
                    throw new RepeatKillException("Seckill repeated");
                }else {
                    //减库存
                    int updateCount=seckillDao.reducenumber(seckillId,nowTime);
                    if(updateCount<=0){
                        //没有更新到记录，秒杀关闭rollback
                        throw new SeckillCloseException("Seckill is closed");
                    }else {
                        //秒杀成功
                        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                        return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                    }
                }
            }catch (SeckillCloseException e1){
                throw e1;
            }catch (RepeatKillException e2){
                throw e2;
            }catch (Exception e){
                logger.error(e.getMessage(),e);
                //所有的编译期异常，转换为运行期异常，spring的声明式事务会帮我做rollback
                throw new SeckillException("Seckill inner error:"+e.getMessage());
            }
    }


}