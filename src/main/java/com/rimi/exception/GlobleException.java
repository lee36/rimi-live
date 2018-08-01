package com.rimi.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 用户处理全局异常的控制类
 */
@ControllerAdvice
public class GlobleException {

    public Object test(){
        return "123";
    }
}
