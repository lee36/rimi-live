package com.rimi.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.ExcludeDefaultListeners;
import javax.servlet.http.HttpServletRequest;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * 用户处理全局异常的控制类
 */
@RestControllerAdvice
public class GlobleException {
    @ExceptionHandler(Exception.class)
    public String timeOutException(Exception e){
        System.out.println("1111111111111111");
        if(e instanceof AuthDeyException){
            return e.getMessage();
        }else if(e instanceof SendMessageException){
            System.out.println(11111);
            return e.getMessage();
        }
       return null;
    }

}
