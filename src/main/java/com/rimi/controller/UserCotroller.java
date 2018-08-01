package com.rimi.controller;

import com.rimi.Vo.ResponseResult;
import com.rimi.componet.BuilderErrorComponet;
import com.rimi.model.Manager;
import com.rimi.form.ManagerForm;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserCotroller {
    @RequestMapping("/")
    public Object test(){
        return ResponseResult.success(null);
    }
    @RequestMapping("/index")
    public Object test1(){
        Manager manager = new Manager(1,"123","123");
        return ResponseResult.error(1001,"用户名错误",manager);
    }
    @PostMapping("/regist")
    public Object testRegister(@Valid ManagerForm manager, BindingResult result){
        if(result.hasErrors()){
          HashMap map=  BuilderErrorComponet.builderError(result);
            return ResponseResult.error(50010,"失败",map);
        }else{
            return ResponseResult.success(null);
        }
    }
}
