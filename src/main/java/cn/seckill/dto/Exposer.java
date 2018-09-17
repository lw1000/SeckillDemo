package cn.seckill.dto;

/**
 * 暴露秒杀接口地址DTO
 * Created by Administrator on 2018/8/21 0021.
 */
public class Exposer {
    //秒杀是否开始
    private boolean exporsed;
    //MD5加密
    private String md5;
    //id
    private long seckillId;
    //系统当前时间
    private long now;
    //开启时间
    private long start;
    //结束时间
    private long end;

    @Override
    public String toString() {
        return "Exposer{" +
                "exporsed=" + exporsed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public Exposer() {
    }

    public Exposer(boolean exporsed, long seckillId) {
        this.exporsed = exporsed;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exporsed, String md5, long seckillId) {
        this.exporsed = exporsed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exporsed, long seckillId, long now, long start, long end) {
        this.exporsed = exporsed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exporsed, long now, long start, long end) {
        this.exporsed = exporsed;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public boolean isExporsed() {
        return exporsed;
    }

    public void setExporsed(boolean exporsed) {
        this.exporsed = exporsed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
