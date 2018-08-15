package com.rimi.aop;

import com.rimi.vo.InMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.elasticsearch.common.settings.loader.YamlSettingsLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class SendMessageAspect {
    @Pointcut("execution(* com.rimi.controller.ChatController.*(..))")
    public void sendMessage(){}
    @Before(value = "sendMessage()")
    public void validateSendMessage(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        InMessage in=(InMessage)args[0];
        if(StringUtils.isEmpty(in.getNickName())){
            throw new RuntimeException("你没有权限");
        }
        return ;
    }
}
