package cn.seckill.service;

import cn.seckill.dto.Exposer;
import cn.seckill.dto.SeckillExecution;
import cn.seckill.entity.Seckill;
import cn.seckill.exception.RepeatKillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Administrator on 2018/9/5 0005.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void testGetAllSeckill() throws Exception {
        List<Seckill> seckillList=seckillService.getAllSeckill();
        logger.info("seckillList={}",seckillList);
    }

    @Test
    public void testQuerySeckillById() throws Exception {
        long sekillId=1000;
        Seckill seckill= seckillService.querySeckillById(sekillId);
        logger.info("秒杀对象是："+"seckill={}",seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        long id=1000;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        logger.info("exposer:{}",exposer);
        //md5=0caf06d220b32a70c7296ea4a2ff58b6
    }

//    @Test
//    public void testExecuteSeckill() throws Exception {
//        long seckillId=1000;
//        long userPhone=13533333331L;
//        String md5="0caf06d220b32a70c7296ea4a2ff58b6";
//        try {
//            SeckillExecution seckillExecution=seckillService.executeSeckill(seckillId,userPhone,md5);
//            logger.info("seckillExecution:{}",seckillExecution);
//        }catch (RepeatKillException e1){
//            logger.error(e1.getMessage(),e1);
//        }catch (Exception e){
//            logger.error(e.getMessage(),e);
//        }
//    }
    //集成测试代码完整逻辑，注意可重复执行
    @Test
    public void testSeckillLogc() throws Exception {
        long seckillId=1000;
        long userPhone=13512341234L;
        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExporsed()){
            logger.info("秒杀开启1：exposer:{}",exposer);
            try {
                SeckillExecution seckillExecution=seckillService.executeSeckill(seckillId,userPhone,exposer.getMd5());
                logger.info("秒杀结果：seckillExecution:{}",seckillExecution);
            }catch (RepeatKillException e1){
                logger.error(e1.getMessage(),e1);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
        }else {
            //警告，秒杀未开启
            logger.warn("秒杀未开启：exposer:{}",exposer);
        }
    }

}