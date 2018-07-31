package com.rimi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rimi.componet.JedisComponet;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RimiLiveApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JedisComponet redisTemplate;

    private Logger logger=LoggerFactory.getLogger(RimiLiveApplication.class);

    @Test
    public void getRedis() throws JSONException, JsonProcessingException {
        boolean set = redisTemplate.set("123", new User(null,"123","666"));

    }

}
