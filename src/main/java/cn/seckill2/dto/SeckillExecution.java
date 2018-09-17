package cn.seckill2.dto;

import cn.seckill2.entity.SuccessKilled;
import cn.seckill2.enums.SeckillStatEnum;

/**
 * 封装秒杀执行后结果
 * Created by Administrator on 2018/8/21 0021.
 */
public class SeckillExecution {

    private long seckillId;
    //秒杀执行结果状态
    private int state;
    //秒杀结果状态的描述
    private String stateInfo;
    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution() {
    }

    //秒杀失败
    public SeckillExecution(long seckillId, SeckillStatEnum seckillStatEnum) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStatInfo();
    }
    //秒杀成功
    public SeckillExecution(long seckillId, SeckillStatEnum seckillStatEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStatInfo();
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
