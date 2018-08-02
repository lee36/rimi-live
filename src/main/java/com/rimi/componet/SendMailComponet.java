package com.rimi.componet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 发送邮想的组件
 */
@Component
public class SendMailComponet {
    @Autowired
    private JavaMailSender sender;
    @Value("${spring.mail.username}")
    private String from;
    public String sendTomail(String to,String token){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("关于激活码");
            message.setText("<a href='http://localhost:8080/anchor/active/"+token+"'>点我激活</a>,请尽快激活，10分钟后失效");
            message.setFrom(from);
            message.setTo(to);
            sender.send(message);
            return token;
        }catch (Exception e){
            return null;
        }
    }
}
