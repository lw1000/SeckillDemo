package cn.seckill.dao;

import cn.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/7/8 0008.
 */
@Repository
public interface SeckillDao {
    /*
    * 减库存
    * return:如果影响行数>1，表示更新的记录行数
    * */
    int reducenumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    Seckill queryById(long seckilled);

    /*
    * 根据偏移量查询秒杀商品列表
    * offset:偏移量
    * limit:偏移量之后要取多少条记录
    * */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);


}
