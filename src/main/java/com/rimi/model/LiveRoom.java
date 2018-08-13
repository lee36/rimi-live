package com.rimi.model;


import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "liveroom_tb")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LiveRoom {
    @Id
    private String id;
    @Column(nullable = false)
    private String livename;
    private Integer type;
    private String keyword;
    private String info;
    private Long hotnum;
    // 直播间的状态，0为未开播，1为开播
    private Integer status;
    // 图像的名字(推流码命名)
    private String livepic;
}
