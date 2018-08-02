package com.rimi.service.impl;

import com.rimi.componet.IdGenneratorComponet;
import com.rimi.componet.UUIDComponet;
import com.rimi.constant.AnchorConstant;
import com.rimi.constant.LiveRoomConstant;
import com.rimi.constant.UserConstant;
import com.rimi.model.Anchor;
import com.rimi.model.LiveRoom;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.LiveRoomRepository;
import com.rimi.service.AnchorService;
import com.rimi.vo.ResponseResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class AnchorServiceImpl implements AnchorService {
    @Autowired
    private AnchorRepository anchorRepository;
    @Autowired
    private LiveRoomRepository liveRoomRepository;
    @Autowired
    private IdGenneratorComponet idGennerator;
    @Override
    public Anchor regist(Anchor anchor) {
        Anchor save = anchorRepository.save(anchor);
        if(save==null){
            return null;
        }
        return save;
    }

    @Override
    @Transactional
    public boolean saveAnchorAndCreateLiveRoom(Anchor anchor) {
        String liveNo = LiveRoomConstant.PREFIX +idGennerator.nextId();
        String formPassword=anchor.getPassword();
        String dbPassword = DigestUtils.md5Hex(formPassword + UserConstant.SALT);
        anchor.setCreateTime(new Timestamp(new Date().getTime()));
        anchor.setId(AnchorConstant.PREFIX + idGennerator.nextId() + "");
        anchor.setLiveNo(liveNo);
        anchor.setStatus(1);
        anchor.setPassword(dbPassword);
        //生成主播
        Anchor regist = anchorRepository.save(anchor);
        //生成直播间
        LiveRoom liveRoom = new LiveRoom();
        liveRoom.setId(liveNo);
        liveRoom.setLivename(anchor.getNickName()+"的直播间");
        liveRoomRepository.save(liveRoom);
        if (regist != null&&liveRoom != null) {
            return true;
        }else{
            return false;
        }
    }
}
