package com.rimi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * Jedis连接池配置类
 */
@Component
public class JedisFactory {
    @Autowired
    private JedisProperties jedisProperties;

    @Bean
    public JedisPool jedisPoolFactory(){
        JedisPool jedisPool = new JedisPool(
                jedisProperties.getHost(),jedisProperties.getPort()
        );
        return jedisPool;
    }
}
