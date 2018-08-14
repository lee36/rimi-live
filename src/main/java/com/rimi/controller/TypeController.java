package com.rimi.controller;

import com.rimi.model.Type;
import com.rimi.service.TypeService;
import com.rimi.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/all")
    public Object getAllType(){
        List<Type> types = typeService.findAllType();
        if(types==null){
            return ResponseResult.error(560,"没有任何板块",null);
        }
        return ResponseResult.success(types);
    }
}
