package com.rimi.service.impl;

import com.rimi.componet.IdGenneratorComponet;
import com.rimi.componet.UUIDComponet;
import com.rimi.constant.AnchorConstant;
import com.rimi.constant.LiveRoomConstant;
import com.rimi.constant.UserConstant;
import com.rimi.form.AnchorForm;
import com.rimi.form.UpdateAnchorForm;
import com.rimi.model.Anchor;
import com.rimi.model.LiveRoom;
import com.rimi.model.UserFocus;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.LiveRoomRepository;
import com.rimi.repository.UserFocusRepository;
import com.rimi.service.AnchorService;
import com.rimi.service.UserFocusService;
import com.rimi.vo.ResponseResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnchorServiceImpl implements AnchorService {
    @Autowired
    private AnchorRepository anchorRepository;
    @Autowired
    private LiveRoomRepository liveRoomRepository;
    @Autowired
    private IdGenneratorComponet idGennerator;
    @Autowired
    private UserFocusRepository userFocusRepository;
    @Value("${live.room.default.type}")
    private int liveRoomType;
    @Value("${live.room.default.info}")
    private String liveRoomInfo;
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
        liveRoom.setType(liveRoomType);
        //关闭状态 没开直播
        liveRoom.setStatus(0);
        //设置默认信息
        liveRoom.setInfo(liveRoomInfo);
        liveRoom.setLivename(anchor.getNickName()+"的直播间");
        liveRoomRepository.save(liveRoom);
        if (regist != null&&liveRoom != null) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public byte[] transPartToBytes(Part part) throws IOException {
        InputStream in = part.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = in.read(buffer)) != -1){
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        }catch (Exception e){

        }finally {
            outputStream.close();
        }
        return null;
    }

    @Override
    public boolean writeFileToPath(byte[] files,File parent,String fileName) throws IOException {
        ByteArrayInputStream in= new ByteArrayInputStream(files);
        InputStreamReader isr = new InputStreamReader(in,"utf-8");
        FileOutputStream out = new FileOutputStream(new File(parent,fileName));
        try {
            int num = 0;
            byte[] bytes = new byte[1024];
            while ((num = in.read(bytes, 0, bytes.length)) != -1) {
                out.write(num);
                out.flush();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            isr.close();
        }
        return false;
    }

    @Override
    public Anchor findByEmail(String email) {
        return anchorRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public boolean updateAnchor(String id, UpdateAnchorForm anchorForm) {
        Anchor one = anchorRepository.findOneById(id);
        if (one==null){
            return false;
        }
        String formPass = anchorForm.getPassword();
        String dbPass = DigestUtils.md5Hex(formPass + UserConstant.SALT);
        String nickName = anchorForm.getNickName();
        // 判断是否有昵称传入
        if (nickName!=null){
            one.setNickName(nickName);
        }
        one.setPassword(dbPass);
        anchorRepository.save(one);
        return true;
    }

    @Override
    @Transactional
    public boolean updateAnchorImg(String id,String filename) {
        Anchor one = anchorRepository.findOneById(id);
        if (one==null){
            return false;
        }
        one.setHeadImg(filename+".jpg");
        anchorRepository.save(one);
        return true;
    }

    @Override
    public Anchor findOneById(String id) {
        Anchor anchor = anchorRepository.findOneById(id);
        if(anchor==null){
            return null;
        }
        return anchor;
    }

    @Override
    @Transactional
    public Map<Object, Object> getAnchorAndLiveRoom(Anchor anchor,String userId) {
        HashMap<Object, Object> map =new HashMap<>();
        try {
            String liveNo = anchor.getLiveNo();
            LiveRoom liveRoom = liveRoomRepository.findOneById(liveNo);
            UserFocus userFocus=userFocusRepository.findByUserId(userId);
            if(userFocus.getAnchorId().equals(anchor.getId())){
                map.put("focus",true);
            }else{
                map.put("focus",false);
            }
            map.put("anchor", anchor);
            map.put("liveRoom", liveRoom);
        }catch (Exception e){
            return null;
        }
        return map;
    }
}
