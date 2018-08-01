package com.rimi.validator;

import com.rimi.constraint.AnchorEmailUnique;
import com.rimi.model.Anchor;
import com.rimi.model.User;
import com.rimi.repository.AnchorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class AnchorEmailUniqueValidator implements ConstraintValidator<AnchorEmailUnique,String> {
    @Autowired
    private AnchorRepository anchorRepository;
    @Override
    public void initialize(AnchorEmailUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Anchor anchor = anchorRepository.findByEmail(value);
        if(anchor!=null){
            //没有该邮箱
            return false;
        }
        return true;
    }
}
