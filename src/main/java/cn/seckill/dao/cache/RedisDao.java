package cn.seckill.dao.cache;

import cn.seckill.entity.Seckill;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Administrator on 2018/9/20 0020.
 */
@Repository
public class RedisDao {
     private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private  JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip,int prot) {
       jedisPool=new JedisPool(ip,prot);
    }

    public RedisDao() {
    }

    public Seckill getSeckill(Long seckillId){
        //Redis操作逻辑
        try {
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+seckillId;
                //get->byte[]->反序列化->Object （Seckillq）
                //第三方序列化
                byte[] bytes=jedis.get(key.getBytes());
                if(bytes!=null){
                    Seckill seckill=schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes,seckill,schema);
                    return seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){

        }

        return null;
    }

    public String putSeckill(Seckill seckill){
        //set Object->序列化->byte[]->发送给Redis
        try {
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+seckill.getSeckillId();
                byte[] bytes=ProtobufIOUtil.toByteArray(seckill,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                //Redis存的数据可以使永久也可以是限时的
                //对于数据库缓存来说一般使用超时机制来保证数据缓存与数据库数据的完整性
                int time=60*60;
                //返回error或OK
                String result=jedis.setex(key.getBytes(),time,bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){

        }
        return null;
    }

}
