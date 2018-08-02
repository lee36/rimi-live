package com.rimi.service.impl;

import com.rimi.model.LiveRoom;
import com.rimi.repository.LiveRoomRepository;
import com.rimi.service.LiveRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiveRoomServiceImpl implements LiveRoomService {
    @Autowired
    private LiveRoomRepository liveRoomRepository;
    @Override
    public LiveRoom createLiveRoom(LiveRoom liveRoom) {
        LiveRoom save = liveRoomRepository.save(liveRoom);
        if(save==null){
            return null;
        }
        return save;
    }
}
