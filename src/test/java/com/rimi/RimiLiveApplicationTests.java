package com.rimi;

import com.rimi.model.User;
import com.rimi.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RimiLiveApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger=LoggerFactory.getLogger(RimiLiveApplication.class);
    @Test
    public void contextLoads() {
        userRepository.save(new User(null,"123","123"));
    }
    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("123",new User(1,"123","123"));
    }
    @Test
    public void getRedis() throws JSONException {
        User o = (User)redisTemplate.opsForValue().get("123");
        System.out.print(o+"==============");

    }

}
