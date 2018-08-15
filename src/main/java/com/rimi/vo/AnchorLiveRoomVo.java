package com.rimi.vo;

import com.rimi.model.Anchor;
import com.rimi.model.LiveRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnchorLiveRoomVo{
    private Anchor anchor;
    private LiveRoom liveRoom;
}
