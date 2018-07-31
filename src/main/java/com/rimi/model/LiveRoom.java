package com.rimi.model;


import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "liveroom_tb")
@AllArgsConstructor
@NoArgsConstructor
public class LiveRoom {
    @Id
    private String id;
    @Column(nullable = false)
    private String livename;
    private Integer type;
    private String keyword;
    private String info;
    private Long hotnum;
    private Integer status;
    private String livepic;
}
