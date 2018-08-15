package com.rimi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "userFocus_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFocus {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    // 用户的id
    @Column(name="user_id")
    private String userId;
    @Column(name="anchor_id")
    private String anchorId;
}
