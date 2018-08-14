package com.rimi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rimi.componet.JedisComponet;
import com.rimi.componet.SendMailComponet;
import com.rimi.constant.UserConstant;
import com.rimi.model.Anchor;
import com.rimi.model.Manager;
import com.rimi.model.User;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.UserRepository;
import com.rimi.service.LiveRoomService;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
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

    @Autowired
    private LiveRoomService liveRoomService;

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
    public void test5(){
        Anchor one = anchorRepository.findOneById("anchor47642384030236672");
        System.out.println(one);
    }

    @Test
    public void test6(){
        liveRoomService.liveStart("13638363279@163.com","123");
    }

    @Test
    public void test7(){
        liveRoomService.liveClose("123");
    }
    @Test
    public void test8(){
        User user=new User();
        user.setId("1");
        user.setNickName("哈哈哈哈");
        user.setPassword("15454512");
        userRepository.save(user);
    }
}
