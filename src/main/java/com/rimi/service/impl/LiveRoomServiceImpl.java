package com.rimi.service.impl;

import com.rimi.model.Anchor;
import com.rimi.model.LiveRoom;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.LiveRoomRepository;
import com.rimi.service.LiveRoomService;
import com.rimi.vo.LiveRoomVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LiveRoomServiceImpl implements LiveRoomService {
    @Autowired
    private LiveRoomRepository liveRoomRepository;
    @Autowired
    private AnchorRepository anchorRepository;
    @Override
    public LiveRoom createLiveRoom(LiveRoom liveRoom) {
        LiveRoom save = liveRoomRepository.save(liveRoom);
        if(save==null){
            return null;
        }
        return save;
    }

    @Override
    public List<LiveRoomVo> findByTypeAndStatus(Integer typeId, Integer status) {
        List<LiveRoom> liveRooms = liveRoomRepository.findByTypeAndStatus(typeId,status);
        List<LiveRoomVo> liveRoomVos=new ArrayList<>();
        for (LiveRoom liveRoom : liveRooms) {
            //获得直播间id
            String id = liveRoom.getId();
            LiveRoomVo liveRoomVo = new LiveRoomVo();
            Anchor anchor = anchorRepository.findByLiveNo(id);
            BeanUtils.copyProperties(liveRoom,liveRoomVo);
            liveRoomVo.setAnchor(anchor);
            liveRoomVos.add(liveRoomVo);
        }
        if(liveRooms!=null){
            return liveRoomVos;
        }
        return null;
    }
}
