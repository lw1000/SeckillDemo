package cn.seckill2.service;

import cn.seckill2.dto.Exposer;
import cn.seckill2.dto.SeckillExecution;
import cn.seckill2.entity.Seckill;
import cn.seckill2.exception.RepeatKillException;
import cn.seckill2.exception.SeckillCloseException;
import cn.seckill2.exception.SeckillException;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21 0021.
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     */
    List<Seckill> getAllSeckill();

    /**
     *根据id查询秒杀记录
     */
    Seckill querySeckillById(long seckillId);

    /**
     * 秒杀开启时输出秒杀地址，否则输出秒杀时间和系统时间
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userphone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
    throws SeckillException,RepeatKillException,SeckillCloseException;
    }
