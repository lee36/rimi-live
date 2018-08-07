package com.rimi.service;

import com.rimi.model.LiveRoom;
import com.rimi.vo.LiveRoomVo;

import java.util.List;

public interface LiveRoomService {
    public LiveRoom createLiveRoom(LiveRoom liveRoom);
    public List<LiveRoomVo> findByTypeAndStatus(Integer typeId, Integer status);
}
