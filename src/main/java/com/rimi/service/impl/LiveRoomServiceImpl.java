package com.rimi.service.impl;

import com.rimi.model.Anchor;
import com.rimi.model.LiveRoom;
import com.rimi.model.Type;
import com.rimi.model.UserFocus;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.LiveRoomRepository;
import com.rimi.repository.TypeRepository;
import com.rimi.repository.UserFocusRepository;
import com.rimi.service.LiveRoomService;
import com.rimi.service.UserFocusService;
import com.rimi.vo.AnchorLiveRoomVo;
import com.rimi.vo.LiveRoomVo;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LiveRoomServiceImpl implements LiveRoomService {
    @Autowired
    private LiveRoomRepository liveRoomRepository;
    @Autowired
    private AnchorRepository anchorRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private UserFocusRepository userFocusRepository;
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

    @Override
    @Transactional
    public Boolean updateLiveRoom(String id, String roomName, String info, int type) {
        Anchor anchor = anchorRepository.findOneById(id);
        if(anchor==null){
            return false;
        }
        String liveNo = anchor.getLiveNo();
        LiveRoom liveRoom = liveRoomRepository.findOneById(liveNo);
        Type one = typeRepository.getOne(type);
        if(!StringUtils.isEmpty(roomName)&&!StringUtils.isEmpty(info)&&one!=null){
            liveRoom.setLivename(roomName);
            liveRoom.setInfo(info);
            liveRoom.setType(type);
            liveRoomRepository.save(liveRoom);
            return true;
        }
        return false;

    }

    @Override
    @Transactional
    public Boolean opConcernAndOpFocus(String anchorId, String email, int flag) {
        if(flag==1){
            //增加操作
            //先添加到数据库中
            UserFocus userFocus = new UserFocus();
            userFocus.setUserId(email);
            userFocus.setAnchorId(anchorId);
            UserFocus save = userFocusRepository.save(userFocus);
            if(save!=null){
                //增加关注度
                Anchor anchor = anchorRepository.findOneById(anchorId);
                if(anchor!=null){
                    String liveNo = anchor.getLiveNo();
                    LiveRoom liveRoom = liveRoomRepository.findOneById(liveNo);
                    Long hotnum = liveRoom.getHotnum();
                    liveRoom.setHotnum(hotnum+1L);
                    liveRoomRepository.save(liveRoom);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else {
            //删除操作
            //从数据库中删除
            Integer count = userFocusRepository.deleteByUserIdAndAnchorId(email, anchorId);
            if(count!=null){
                //减少关注度
                Anchor anchor = anchorRepository.findOneById(anchorId);
                if(anchor!=null){
                    String liveNo = anchor.getLiveNo();
                    LiveRoom liveRoom = liveRoomRepository.findOneById(liveNo);
                    Long hotnum = liveRoom.getHotnum();
                    liveRoom.setHotnum(hotnum-1L);
                    liveRoomRepository.save(liveRoom);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }

        }
    }

    @Override
    @Transactional
    public List<AnchorLiveRoomVo> findFocusLiveRoom(String email) {
        List<AnchorLiveRoomVo> list=new ArrayList<>();
        //通过关注表查到主播的id
        List<UserFocus> AnchorList = userFocusRepository.findByUserId(email);
        //循环获取主播id
        for (UserFocus anchor : AnchorList) {
            String anchorId = anchor.getAnchorId();
            //通过id查找主播
            Anchor exit = anchorRepository.findOneById(anchorId);
            //找出直播间id
            String liveNo = exit.getLiveNo();
            //找出直播间
            LiveRoom liveRoom = liveRoomRepository.findOneById(liveNo);
            AnchorLiveRoomVo anchorLiveRoom = new AnchorLiveRoomVo();
            anchorLiveRoom.setAnchor(exit);
            anchorLiveRoom.setLiveRoom(liveRoom);
            list.add(anchorLiveRoom);
        }
        return list;
    }
}
