package com.rimi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rimi.componet.JedisComponet;
import com.rimi.componet.SendMailComponet;
import com.rimi.constant.UserConstant;
import com.rimi.model.Anchor;
import com.rimi.model.Manager;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.UserRepository;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private AnchorRepository anchorRepository;
    @Autowired
    private SendMailComponet sendMailComponet;
    private Logger logger=LoggerFactory.getLogger(RimiLiveApplication.class);

    @Test
    public void getRedis() throws JSONException, JsonProcessingException {
        boolean set = redisTemplate.set("aaa","123",null);
    }
    @Test
    public void testRedis() throws IOException {
        Manager user = redisTemplate.get("123", Manager.class);
        System.out.print(user);
    }
    @Test
    public void test1(){
        Anchor email = anchorRepository.findByEmail("2950925007@qq.com");
        System.out.print(email+"===========");
    }
    @Test
    //e56ea2d45a3535f7c0ada18b56814db2
    //e56ea2d45a3535f7c0ada18b56814db2
    //e56ea2d45a3535f7c0ada18b56814db2
    public void testPass(){
        String s  = DigestUtils.md5Hex("123456"+UserConstant.SALT);
        System.out.println(s);
    }

}
