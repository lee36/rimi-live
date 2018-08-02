package com.rimi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rimi.componet.*;
import com.rimi.constant.UserConstant;
import com.rimi.vo.ResponseResult;
import com.rimi.constant.AnchorConstant;
import com.rimi.form.AnchorForm;
import com.rimi.model.Anchor;
import com.rimi.service.AnchorService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * 主播的控制层
 */
@RestController
@RequestMapping("/anchor")
public class AnchorController {

    @Autowired
    private AnchorService anchorService;
    @Autowired
    private JedisComponet jedisComponet;
    @Autowired
    private IdGenneratorComponet idGennerator;
    @Autowired
    private SendMailComponet sendMailComponet;
    @Value("${redis.disable.time}")
    private int disableTime;
    @PostMapping("/regist")
    public Object AnchorRegist(@Valid AnchorForm anchorForm,
                               BindingResult result) throws JsonProcessingException {
        //验证不通过
       if(result.hasErrors()){
           HashMap hashMap = BuilderErrorComponet.builderError(result);
           return ResponseResult.error(500,"注册失败",hashMap);
       }
        //验证通过后
        String token=UUIDComponet.uuid();
        //存入redis中
        jedisComponet.set(token,anchorForm,disableTime);
        //发送邮件
        try {
            sendMailComponet.sendTomail(anchorForm.getEmail(), token);
            //发送成功后
            return ResponseResult.success(null);
        }catch (Exception e){
            //发送异常后
            return ResponseResult.error(510,"邮件发送异常",null);
        }

    }

    @GetMapping("/active/{token}")
    public Object AnchorActive(@PathVariable String token) throws IOException {

        //有人伪造
        if (token == null) {
            //不存在此token的时候
            return ResponseResult.error(520, "请先去注册", null);
        }
        //否则
        AnchorForm anchorForm = jedisComponet.get(token, AnchorForm.class);
        if (anchorForm == null) {
            //有人乱输入了链接或者链接失效
            return ResponseResult.error(530, "这不是一个有效的连接", null);
        } else {
            //经过验证后将form表单对象copy到实体中
            Anchor anchor = new Anchor();
            BeanUtils.copyProperties(anchorForm, anchor);
            //插入主播的同时生成主播间
            boolean b = anchorService.saveAnchorAndCreateLiveRoom(anchor);
            if(b){
                return ResponseResult.success(null);
            }else{
                return ResponseResult.error(540,"申请失败",null);
            }
        }
    }
}
