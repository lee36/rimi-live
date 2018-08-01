package com.rimi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "anchor_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Anchor {
    @Id
    private String id;
    @Column(nullable = false,name = "nick_name")
    private String nickName;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name="head_img")
    private String headImg="/head/default.jpg";
    @Column(unique = true,name="phone_number")
    private String phoneNumber;
    @Column(name="live_no")
    private String liveNo;
    private String medal;
    private Integer status;
    @Column(name="create_time")
    private Timestamp createTime;
    @Column(nullable = false)
    private int gender;
}
