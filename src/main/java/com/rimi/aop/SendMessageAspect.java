package com.rimi.aop;

import com.rimi.exception.SendMessageException;
import com.rimi.model.Anchor;
import com.rimi.model.User;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.UserRepository;
import com.rimi.vo.InMessage;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.elasticsearch.common.settings.loader.YamlSettingsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class SendMessageAspect {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnchorRepository anchorRepository;
    @Pointcut("execution(* com.rimi.controller.ChatController.*(..))")
    public void sendMessage(){}
    @Before(value = "sendMessage()")
    public void validateSendMessage(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        InMessage in=(InMessage)args[0];
        String nickName = in.getNickName();
        if("null".equals(nickName)){
            throw new SendMessageException("你没有权限");
        }
        User user = userRepository.findByNickName(nickName);
        if(user!=null){
            if(user.getStatus()==3){
                throw new SendMessageException("请联系管理员");
            }
            return ;
        }else{
            Anchor anchor = anchorRepository.findByNickName(nickName);
            if(anchor!=null){
                if(anchor.getStatus()==3){
                    throw new SendMessageException("请联系管理员");
                }
                return ;
            }
        }

    }
}
