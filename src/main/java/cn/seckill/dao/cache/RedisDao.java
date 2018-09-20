package cn.seckill.dao.cache;

import cn.seckill.entity.Seckill;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Administrator on 2018/9/20 0020.
 */
public class RedisDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private JedisPool jedisPool;
    private int port;
    private String ip;

    public RedisDao(String ip, int port) {
        logger.info("---------------------------------ip:{},port:{}",ip,port);
        this.port = port;
        this.ip = ip;
    }

    //Serialize function
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(long seckillId) {
        jedisPool = new JedisPool(ip, port);
        //redis operate
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //由于redis内部没有实现序列化方法,而且jdk自带的implaments Serializable比较慢,会影响并发,因此需要使用第三方序列化方法.
                byte[] bytes = jedis.get(key.getBytes());
                if(null != bytes){
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    //reSerialize
                    return seckill;
                }
            } finally {
                jedisPool.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return null;
    }

    public String putSeckill(Seckill seckill) {
        jedisPool = new JedisPool(ip, port);
        //set Object(seckill) ->Serialize -> byte[]
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckill:"+seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //time out  cache
                int timeout = 60*60;
                String result = jedis.setex(key.getBytes(),timeout,bytes);
                return result;
            }finally {
                jedisPool.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
