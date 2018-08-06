package com.rimi.controller;

import com.rimi.vo.InMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * 用户实现聊天的控制层
 */
@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/messageReciver")
    public void messageReciver(InMessage inMessage){
       System.out.println(inMessage+"=========");
       simpMessagingTemplate.convertAndSend("/topic/"+inMessage.getRoomId(),inMessage.getMsg());
    }
}
