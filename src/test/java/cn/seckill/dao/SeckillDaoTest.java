package cn.seckill.dao;

import cn.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/20 0020.
 */

/**
 * 配置spring和Junit整合，Junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;
    @Test
    public void reducenumber() throws Exception {
        Date killtime=new Date();
        long seckillId=1000;
        int update=seckillDao.reducenumber(seckillId,killtime);
        System.out.println(update);
    }

    @Test
    public void queryById() throws Exception {
        long id=1001;
        Seckill seckill=seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);

    }

    @Test
    public void queryAll() throws Exception {
        //java没有保存形参的记录queryAll(0,100)-->queryAll(arg0,arg1);
        List<Seckill> seckillList=seckillDao.queryAll(0,100);
        for (Seckill seckill:seckillList){
            System.out.println(seckillList);
        }

    }

}