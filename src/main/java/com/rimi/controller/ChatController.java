package com.rimi.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * 用户实现聊天的控制层
 */
@Controller
public class ChatController {
    @MessageMapping("/messageReciver")
    public String messageReciver(String msg){
       System.out.println(msg+"=========");
       return null;
    }
}
