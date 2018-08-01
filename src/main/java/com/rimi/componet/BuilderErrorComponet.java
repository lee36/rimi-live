package com.rimi.componet;


import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

/**
 * 构建错误信息
 */
public class BuilderErrorComponet {
    public static HashMap builderError(BindingResult result){
        List<FieldError> errors = result.getFieldErrors();
        HashMap<Object, Object> map = new HashMap<>();
        for (FieldError error : errors) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            map.put(field,message);
        }
        return map;
    }
}
