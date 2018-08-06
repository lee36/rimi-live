package com.rimi.validator;

import com.rimi.constraint.AnchorEmailUnique;
import com.rimi.model.Anchor;
import com.rimi.model.User;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.UserRepository;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.*;
import javax.validation.executable.ExecutableValidator;
import java.lang.annotation.Annotation;


public class AnchorEmailUniqueValidator implements ConstraintValidator<AnchorEmailUnique,String> {
    @Autowired
    private AnchorRepository anchorRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void initialize(AnchorEmailUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Anchor anchor = anchorRepository.findByEmail(value);
        User user = userRepository.findByEmail(value);
        if(anchor!=null||user!=null){
            //没有该邮箱
            return false;
        }
        return true;
    }
}