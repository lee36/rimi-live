package com.rimi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Entity
@Table(name="manager_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager implements Serializable {
    @Id
    private Integer id;
    @NotNull(message = "管理员账户不能为空")
    private String username;
    @NotNull(message = "管理员密码不能为空")
    private String password;
}
