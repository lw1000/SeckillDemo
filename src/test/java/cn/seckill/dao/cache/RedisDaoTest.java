package cn.seckill.dao.cache;

import cn.seckill.dao.SeckillDao;
import cn.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2018/9/20 0020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class RedisDaoTest {
    private long id=1001;

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private RedisDao redisDao;
    @Test
    public void testSeckill()throws Exception{
        Seckill seckill=redisDao.getSeckill(id);
        if(seckill==null){
            seckill=seckillDao.queryById(id);
            if(seckill!=null){
                String result= redisDao.putSeckill(seckill);
                System.out.println("result:"+result);
                seckill= redisDao.getSeckill(id);
               System.out.print("seckill:"+seckill.toString());
            }
        }
   }

}