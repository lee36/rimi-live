package com.rimi.form;

import com.rimi.constraint.AnchorNickNamelUnique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserForm {
    @NotNull(message = "id不能为空")
    private String id;
    @AnchorNickNamelUnique(message = "该昵称已存在")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{4,8}$",message = "请输入4-8个中文字符")
    private String nickName;
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "^(\\w){6,15}$",message = "密码应该在6-15位之间")
    private String password;
}
