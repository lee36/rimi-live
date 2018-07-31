package com.rimi.componet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;

@Component
public class JedisComponet {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 添加和修改redis
     * @param key
     * @param obj
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public <T> boolean set(String key,T obj) throws JsonProcessingException {
        Jedis jedis = jedisPool.getResource();
        String value = beanToString(obj);
        try {
            String set = jedis.set(key, value);
        }catch (Exception e){
            return false;
        }
        return true;

    }

    /**
     * 获取相应对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> T get(String key,Class clazz) throws IOException {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get(key);
        T t = StringToBean(s, (Class<T>) clazz);
        return t;
    }


    //将对象转换成字符串
    private <T> String beanToString(T value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if(value==null) {
            return null;
        }
        Class<?> clazz= value.getClass();
        if(clazz==int.class||clazz==Integer.class) {
            return ""+value;
        }else if(clazz==String.class) {
            return (String)value;
        }else if(clazz==long.class||clazz==Long.class) {
            return ""+value;
        }else {
            return mapper.writeValueAsString(value);
        }
    }

    //将字符串转换成对象
    private <T> T StringToBean(String str,Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if(str==null||str.length()<0||clazz==null) {
            return null;
        }
        if(clazz==int.class||clazz==Integer.class) {
            return (T) Integer.valueOf(str);
        }else if(clazz==String.class) {
            return (T) str;
        }else if(clazz==long.class||clazz==Long.class) {
            return (T) Long.valueOf(str);
        }else {
            return (T)mapper.readValue(str,clazz);
        }
    }
}
