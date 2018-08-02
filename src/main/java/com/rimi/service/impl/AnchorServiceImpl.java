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

import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.*;
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
}
