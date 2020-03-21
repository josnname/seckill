package com.learn.seckill.Redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

//    public String getGoodsListHtml(KeyPrefix prefix,String key){
//        Jedis jedis = null;
//        try{
//            jedis = jedisPool.getResource();
//            String realKey = prefix.getPrefix() + key;
//            String str = jedis.get(realKey);
//            return str;
//        }finally {
//            ReturnToPool(jedis);
//        }
//    }

    public <T> T get(KeyPrefix prefix,String key,Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t= StringToBean(str,clazz);
            return t;
        }finally {
            ReturnToPool(jedis);
        }
    }
    public <T> Boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String str = BeanToString(value);
            jedis.set(realKey,str);
            System.out.println("过期时间："+ prefix.expireSeconds());
            jedis.expire(realKey,prefix.expireSeconds());
            return true;
        }finally {
            ReturnToPool(jedis);
        }
    }
    private <T> T StringToBean(String str,Class<T> clazz){
        if(str ==null || str.length() <=0 || clazz ==null){
            return null;
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class){
            return  (T)str;
        }else if(clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }else{
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    private <T> String BeanToString(T value){
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class){
            return ""+value;
        }else if(clazz == String.class){
            return  (String)value;
        }else if(clazz == long.class || clazz == Long.class){
            return ""+ value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    private void ReturnToPool(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }


}
