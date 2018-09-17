package cn.seckill.dao;

import cn.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018/7/9 0009.
 */
public interface SuccessKilledDao {

    /*
    * 插入购买明细，可过滤重复
    * return:插入的行数
    * */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /*
    * 根据id查询SuccessKilled并携带秒杀产品对象实体
    * */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
