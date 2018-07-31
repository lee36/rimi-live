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
    @Value("${mail.auth.number}")
    private String tip;
    @Value("${spring.mail.username}")
    private String from;
    public String sendTomail(String to){
        System.out.println(tip);
        String token = UUID.randomUUID().toString().replace("-","");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("关于激活码");
            message.setText(String.format(tip,token));
            message.setFrom(from);
            message.setTo(to);
            sender.send(message);
            return token;
        }catch (Exception e){
            return null;
        }
    }
}
