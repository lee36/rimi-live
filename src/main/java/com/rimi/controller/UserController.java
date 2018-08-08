package com.rimi.controller;


import com.rimi.componet.*;
import com.rimi.constant.AnchorConstant;
import com.rimi.constant.UserConstant;
import com.rimi.form.AnchorForm;
import com.rimi.form.UpdateAnchorForm;
import com.rimi.form.UpdateUserForm;
import com.rimi.form.UserForm;
import com.rimi.model.Anchor;
import com.rimi.model.User;
import com.rimi.service.AnchorService;
import com.rimi.service.UserService;
import com.rimi.service.impl.UserServiceImpl;
import com.rimi.vo.ResponseResult;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
* 用户的控制层
* */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    // user的service层
    @Autowired
    private UserService userService;
    @Autowired
    private AnchorService anchorService;
    // 操作redis的工具类
    @Autowired
    private JedisComponet jedisComponet;

    // 生成id的工具类
    @Autowired
    private IdGenneratorComponet idGenneratorComponet;

    // 发送email的工具类
    @Autowired
    private SendMailComponet sendMailComponet;

    @Value("${file.save.path}")
    private String path;
    @Value("${redis.disable.time}")
    private int disableTime;

    @PostMapping(value = "/regist",produces = "application/json;application/html")
    public Object userRegist(@Valid UserForm userForm, BindingResult result, MultipartFile file) throws IOException {
        System.out.println(userForm.getNickName()+"========================");
        //验证不通过
        if(result.hasErrors()){
            HashMap hashMap = BuilderErrorComponet.builderError(result);
            return ResponseResult.error(500,"注册失败",hashMap);
        }
        if(file!=null){
            //生成文件的名字写入磁盘
            String fileName = UUIDComponet.uuid();
            userForm.setHeadImg(fileName);
            File parent = new File(path);
            if(!parent.exists()){
                parent.mkdirs();
            }
            System.out.println(file+"==========");
            file.transferTo(new File(parent,fileName+".jpg"));
            System.out.println("上传文件成功!====");
        }
        // 转化为用户对象并保存到数据库
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setId(UserConstant.PREFIX + idGenneratorComponet.nextId() + "");
        user.setCreateTime(new Timestamp(new Date().getTime()));
        user.setStatus(1);
        userService.regist(user);
        return ResponseResult.success(null);
        // 邮箱激活需要的代码
//        String token=UUIDComponet.uuid();
//        //存入redis中
//        jedisComponet.set(token,userForm,disableTime);
//        System.out.println("放在redis里面了");
//        //发送邮件
//        try {
//            sendMailComponet.sendTomail(userForm.getEmail(), token);
//            System.out.println("邮箱已发送!");
//            //发送成功后
//            return ResponseResult.success(null);
//        }catch (Exception e){
//            //发送异常后
//            return ResponseResult.error(510,"邮件发送异常",null);
//        }
    }

    @PostMapping(value = "/login")
    public Object login(String email,String password){
        System.out.println(email+password);
        Object object = userService.login(email, password);
        if (object==null){
            return ResponseResult.error(503,"fail",null);
        }
        else {
            return ResponseResult.success(object);
        }
    }

//    @GetMapping("/useractive/{token}")
//    public Object AnchorActive(@PathVariable String token) throws IOException {
//        //有人伪造
//        if (token == null) {
//            //不存在此token的时候
//            return ResponseResult.error(520, "请先去注册", null);
//        }
//        //否则
//        UserForm userForm = jedisComponet.get(token, UserForm.class);
//        if (userForm == null) {
//            //有人乱输入了链接或者链接失效
//            return ResponseResult.error(530, "这不是一个有效的连接", null);
//        } else {
//            //经过验证后将form表单对象copy到实体中
//            User user = new User();
//            BeanUtils.copyProperties(userForm, user);
//            //插入主播的同时生成主播间
//            boolean b = userService.regist(user);
//            if(b){
//                return ResponseResult.success(null);
//            }else{
//                return ResponseResult.error(540,"申请失败",null);
//            }
//        }
//    }
    @GetMapping("/get")
    public Object get(String email){
        if(email==null){
            return ResponseResult.error(580,"请先登录",null);
        }
        User user = userService.findByEmail(email);
        if(user==null){
            Anchor anchor = anchorService.findByEmail(email);
            if(anchor==null){
                return ResponseResult.error(580,"请先登录",null);
            }else{
                return ResponseResult.success(anchor);
            }
        }else{
            return ResponseResult.success(user);
        }
    }

    @GetMapping(value = "/getcode")
    public Object getCode(String email){
        Long code = idGenneratorComponet.nextId();
        if(email==null){
            return ResponseResult.error(580,"请先登录",null);
        }
        Anchor anchor = anchorService.findByEmail(email);
        if(anchor==null){
            return ResponseResult.error(580,"请先登录",null);
        }else{
            return ResponseResult.success(code);
        }
    }

    @PostMapping(value = "/updateUser")
    public Object updateAnchor(@Valid UpdateUserForm userForm, BindingResult result){
        if(result.hasErrors()){
            return ResponseResult.error(590,"修改失败",null);
        }
        String id = userForm.getId();
        if(id==null){
            return ResponseResult.error(590,"修改失败",null);
        }
        boolean b = userService.updateUser(userForm.getId(), userForm);
        if(b){
            //修改成功
            return ResponseResult.success(null);
        }else{
            return ResponseResult.error(590,"修改失败",null);
        }
    }
}
