package cn.seckill2.entity;

import java.util.Date;

/**
 * Created by Administrator on 2018/7/8 0008.
 * 秒杀成功明细表
 */
public class SuccessKilled {

    private long userPhone;//用户手机号
    private short state;//状态
    private Date createTime;//创建时间

    private Seckill seckill;

    public SuccessKilled() {
    }

    public SuccessKilled(long userPhone, short state, Date createTime, Seckill seckill) {
        this.userPhone = userPhone;
        this.state = state;
        this.createTime = createTime;
        this.seckill = seckill;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", seckill=" + seckill +
                '}';
    }
}
