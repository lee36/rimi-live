package com.rimi.form;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnchorForm {
    @NotNull(message = "昵称不能为空")
    private String nickName;
    @Email(message = "邮箱格式不正确")
    @NotNull(message = "邮箱不能为空")
    private String email;
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "^(\\w){6,15}$",message = "密码应该在6-15位之间")
    private String password;
    private String headImg="/head/default.jpg";
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$",message = "请输入正确的手机格式")
    private String phoneNumber;
    private String liveNo;
    private String medal="初来乍到";
    private Integer status;
    private Timestamp createTime;
    @NotNull(message = "请选择你的性别")
    private Integer gender;

}
