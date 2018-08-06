package com.rimi.form;


import com.rimi.constraint.AnchorEmailUnique;
import com.rimi.constraint.AnchorNickNamelUnique;
import com.rimi.constraint.AnchorPhoneUnique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    @NotNull(message = "昵称不能为空")
    @AnchorNickNamelUnique(message = "该昵称已存在")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{4,8}$",message = "请输入4-8个中文字符")
    private String nickName;
    @NotNull(message = "邮箱不能为空")
    @Pattern(regexp = "^[0-9A-Za-z][\\.-_0-9A-Za-z]*@[0-9A-Za-z]+(\\.[0-9A-Za-z]+)+$",message = "邮箱格式不正确")
    @AnchorEmailUnique(message = "邮箱已经存在")
    private String email;
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "^(\\w){6,15}$",message = "密码应该在6-15位之间")
    private String password;
    private String headImg="default.jpg";
    private String medal="初来乍到";
    private Integer status=0;
    private Timestamp createTime;
    @NotNull(message = "请选择你的性别")
    private Integer gender;
}
