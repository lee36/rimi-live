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
    private Integer id;
    // 用户的id
    @Column(name="user_id")
    private String userId;
    @Column(name="anchor_id")
    private String anchorId;
}
