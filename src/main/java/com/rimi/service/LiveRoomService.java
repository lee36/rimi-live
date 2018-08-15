package com.rimi.service;

import com.rimi.model.LiveRoom;
import com.rimi.vo.AnchorLiveRoomVo;
import com.rimi.vo.LiveRoomVo;

import java.util.List;
import java.util.Map;

public interface LiveRoomService {
    public LiveRoom createLiveRoom(LiveRoom liveRoom);
    public List<LiveRoomVo> findByTypeAndStatus(Integer typeId, Integer status);
    public boolean liveStart(String email,String code);
    public boolean liveClose(String code);
    public Boolean updateLiveRoom(String id,String roomName,String info,int type);
    public Boolean opConcernAndOpFocus(String anchorId, String email, int flag);

    public List<AnchorLiveRoomVo> findFocusLiveRoom(String email);
}
