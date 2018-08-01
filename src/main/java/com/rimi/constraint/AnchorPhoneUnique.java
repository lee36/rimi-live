package com.rimi.constraint;


import com.rimi.validator.AnchorPhoneUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy= AnchorPhoneUniqueValidator.class)
public @interface AnchorPhoneUnique {
    /**
     * 用来定义默认得消息模版, 当这个约束条件被验证失败的时候,通过此属性来输出错误信息.
     * @return
     */
    String message() default "你注册的手机已经存在";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
