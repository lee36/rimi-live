package com.rimi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

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
