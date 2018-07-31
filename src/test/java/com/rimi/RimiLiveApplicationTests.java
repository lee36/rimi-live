package com.rimi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rimi.componet.JedisComponet;
import com.rimi.componet.SendMailComponet;
import com.rimi.model.User;
import com.rimi.repository.UserRepository;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RimiLiveApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JedisComponet redisTemplate;
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private SendMailComponet sendMailComponet;
    private Logger logger=LoggerFactory.getLogger(RimiLiveApplication.class);

    @Test
    public void getRedis() throws JSONException, JsonProcessingException {
        boolean set = redisTemplate.set("aaa","123",null);
    }
    @Test
    public void testRedis() throws IOException {
        User user = redisTemplate.get("123", User.class);
        System.out.print(user);
    }
    @Test
    public void testMail(){
          sendMailComponet.sendTomail("1213028676@qq.com");
    }
}
