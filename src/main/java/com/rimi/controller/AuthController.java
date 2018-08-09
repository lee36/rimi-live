package com.rimi.controller;

import com.rimi.componet.JedisComponet;
import com.rimi.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    // 操作redis的工具类
    @Autowired
    private JedisComponet jedisComponet;

    @PostMapping(value = "/publish")
    public Object pushAuth(String name,HttpServletResponse response){
        // 判断是否有该权限
        if (jedisComponet.get(name)==null){
            response.setStatus(500);
        }
        else {
            response.setStatus(200);
        }
        return ResponseResult.success(null);
    }
}
