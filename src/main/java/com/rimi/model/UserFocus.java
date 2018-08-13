package com.rimi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userFocus_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFocus {
    @Id
    // 用户的id
    private String id;
    // 关注的主播id
    private String focus;
}
