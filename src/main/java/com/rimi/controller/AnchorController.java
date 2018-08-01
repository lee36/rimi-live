package com.rimi.controller;

import com.rimi.Vo.ResponseResult;
import com.rimi.componet.BuilderErrorComponet;
import com.rimi.componet.IdGenneratorComponet;
import com.rimi.constant.AnchorConstant;
import com.rimi.form.AnchorForm;
import com.rimi.model.Anchor;
import com.rimi.service.AnchorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    private IdGenneratorComponet idGennerator;

    @PostMapping("/regist")
    public Object AnchorRegist(@Valid AnchorForm anchorForm,
                               BindingResult result){
       if(result.hasErrors()){
           HashMap hashMap = BuilderErrorComponet.builderError(result);
           return ResponseResult.error(500,"注册失败",hashMap);
       }
        Anchor anchor = new Anchor();
        BeanUtils.copyProperties(anchorForm,anchor);
        anchor.setCreateTime(new Timestamp(new Date().getTime()));
        anchor.setId(AnchorConstant.PREFIX+idGennerator.nextId()+"");
        anchor.setLiveNo(UUID.randomUUID().toString());
        anchor.setStatus(0);
        Anchor regist = anchorService.regist(anchor);
        if(regist!=null){
            return ResponseResult.success(null);
        }
        return null;
    }
}
