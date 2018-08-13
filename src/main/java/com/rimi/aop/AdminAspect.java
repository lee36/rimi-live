package com.rimi.aop;

import ch.qos.logback.classic.pattern.SyslogStartConverter;
import com.rimi.exception.AuthDeyException;
import com.rimi.model.Manager;
import com.rimi.repository.AdminRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户的切面类
 */

@Component
@Aspect
public class AdminAspect {
    @Autowired
    private AdminRepository adminRepository;

    @Pointcut("execution(* com.rimi.controller.AdminController.Op*(..))")
    public void adminAspect(){}
    /**
     * 检验权限
     */
    @Before(value = "adminAspect()")
    public void checkAuth(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        //获取用户名
        String username=(String)args[1];
        //获取密码
        String password=(String)args[2];

        if(username==null||password==null){
            throw new AuthDeyException("没有足够的权限");
        }
        Manager manager = adminRepository.findByUsername(username);
        if(!manager.getPassword().equals(password)){
            throw new AuthDeyException("没有足够的权限");
        }
    }
}
