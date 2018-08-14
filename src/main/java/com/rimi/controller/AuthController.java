package com.rimi.controller;

import com.rimi.componet.JedisComponet;
import com.rimi.service.LiveRoomService;
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

    @Autowired
    private LiveRoomService liveRoomService;

    // 权限验证（判断是否有推流权限）
    @PostMapping(value = "/publish")
    public Object pushAuth(String name,HttpServletResponse response){
        System.out.println(name);
        // 判断是否有该权限
        String email = jedisComponet.get(name);
        System.out.println(email);
        if (email==null){
            response.setStatus(500);
        }
        else {
            // 保存推流码到该主播的数据库表中（设置直播间状态为1）
            if (liveRoomService.liveStart(email,name)){
                response.setStatus(200);
            }else{
                response.setStatus(500);
            }
        }
        return ResponseResult.success(null);
    }

    // 关闭流
    @PostMapping(value = "/close")
    public Object closeFlow(String name,HttpServletResponse response){
        // 设置直播间状态为0
        if (liveRoomService.liveClose(name)){
            response.setStatus(200);
        }else{
            response.setStatus(500);
        }
        return ResponseResult.success(null);
    }
}
