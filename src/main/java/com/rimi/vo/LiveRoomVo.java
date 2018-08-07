package com.rimi.vo;

import com.rimi.model.Anchor;
import com.rimi.model.LiveRoom;
import lombok.Data;

@Data
public class LiveRoomVo extends LiveRoom {
    private Anchor anchor;
}
