package com.rimi.service.impl;

import com.rimi.model.Anchor;
import com.rimi.model.LiveRoom;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.LiveRoomRepository;
import com.rimi.service.LiveRoomService;
import com.rimi.vo.LiveRoomVo;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
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

    // 开始直播
    @Override
    @Transactional
    public boolean liveStart(String email, String code) {
        Anchor anchor = anchorRepository.findByEmail(email);
        if (anchor==null){
            return false;
        }
        String liveno = anchor.getLiveNo();
        LiveRoom liveRoom = liveRoomRepository.findOneById(liveno);
        if (liveRoom==null){
            return false;
        }
        // 修改数据
        liveRoom.setStatus(1);
        liveRoom.setLivepic(code);
        liveRoomRepository.save(liveRoom);
        return true;
    }

    // 关闭直播间
    @Override
    @Transactional
    public boolean liveClose(String code) {
        LiveRoom liveRoom = liveRoomRepository.findOneByLivepic(code);
        if (liveRoom==null){
            return false;
        }
        // 修改数据
        liveRoom.setStatus(0);
        liveRoomRepository.save(liveRoom);
        return true;
    }
}
