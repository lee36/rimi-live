package com.rimi.validator;

import com.rimi.constraint.AnchorPhoneUnique;
import com.rimi.model.Anchor;
import com.rimi.repository.AnchorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AnchorPhoneUniqueValidator implements ConstraintValidator<AnchorPhoneUnique,String> {
    @Autowired
    private AnchorRepository anchorRepository;
    @Override
    public void initialize(AnchorPhoneUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Anchor anchor = anchorRepository.findByPhoneNumber(value);
        if(anchor!=null){
            //没有该手机号
            return false;
        }
        return true;
    }
}
