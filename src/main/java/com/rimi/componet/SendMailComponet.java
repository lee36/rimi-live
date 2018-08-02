package com.rimi.componet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    public String sendTomail(String to,String token) throws MessagingException {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//
//            message.setText("<a href='http://localhost:8080/anchor/active/"+token+"'>点我激活</a>,请尽快激活，10分钟后失效");
//            message.setFrom(from);
//            message.setTo(to);
//            sender.send(message);
//            return token;
//        }catch (Exception e){
//            return null;
//        }
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);   //true表示需要创建一个multipart message
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("激活邮件");
            helper.setText("<a href=http://localhost:8080/anchor/active/"+token+">点我激活</a>,请尽快激活，10分钟后失效", true);
            sender.send(message);
            System.out.println("html邮件发送成功");
            return token;
        } catch (MessagingException e) {
            System.out.println("发送html邮件时发生异常！"+e);
        }
            return null;
    }
}
