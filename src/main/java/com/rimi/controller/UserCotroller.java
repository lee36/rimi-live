package com.rimi.controller;

import com.rimi.Vo.ResponseResult;
import com.rimi.componet.BuilderErrorComponet;
import com.rimi.model.Manager;
import com.rimi.form.ManagerForm;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;

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
    @PostMapping(name="/regist1",produces="application/json;charset=UTF-8")
    public Object testRegister(@Valid ManagerForm manager, BindingResult result){
        System.out.println(manager+"========");
        if(result.hasErrors()){
          HashMap map=  BuilderErrorComponet.builderError(result);
            return ResponseResult.error(50010,"失败",map);
        }else{
            return ResponseResult.success(null);
        }
    }

}
