package com.rimi.validator;

import com.rimi.constraint.AnchorNickNamelUnique;
import com.rimi.model.Anchor;
import com.rimi.model.User;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class AnchorNickNameUniqueValidator implements ConstraintValidator<AnchorNickNamelUnique,String> {
    @Autowired
    private AnchorRepository anchorRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void initialize(AnchorNickNamelUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Anchor anchor = anchorRepository.findByNickName(value);
        User user = userRepository.findByNickName(value);
        if(anchor!=null||user!=null){
            //没有改昵称的用户或者管理员
            return false;
        }
        return true;
    }
}
