package com.rimi.controller;

import com.rimi.model.Anchor;
import com.rimi.model.LiveRoom;
import com.rimi.model.Type;
import com.rimi.service.AnchorService;
import com.rimi.service.LiveRoomService;
import com.rimi.service.TypeService;
import com.rimi.vo.LiveRoomVo;
import com.rimi.vo.ResponseResult;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/liveroom")
public class LiveRoomController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private LiveRoomService liveRoomService;
    @Autowired
    private AnchorService anchorService;

    @GetMapping("/all")
    public Object showLiveRoomsByType(){
        List<Type> types = typeService.findAllType();
        //构建
        List<HashMap<Object, Object>> maps = new ArrayList<>();
        for (Type type : types) {

            HashMap<Object, Object> each = new HashMap<>();
            each.put("typeName",type.getTypename());
            //获取type的id
            Integer typeId = type.getId();
            List<LiveRoomVo> liveRooms = liveRoomService.findByTypeAndStatus(typeId,1);
            if(liveRooms.size()<1){
                each.put("liveRooms",null);
            }else{
                each.put("liveRooms",liveRooms);
            }

            maps.add(each);
        }
        return ResponseResult.success(maps);
    }
    @PostMapping("/updateRoom")
    public Object updateRoom(String id,String roomName,String info,int type){
        System.out.println(11111);
        Anchor anchor = anchorService.findOneById(id);
        if(anchor==null){
            ResponseResult.error(580,"对不起，没有权限",null);
        }
        //修改主播的直播间信息
        Boolean b = liveRoomService.updateLiveRoom(id,roomName,info,type);
        //通过lIveNo修改
        if(b){
            return ResponseResult.success(null);
        }
        return ResponseResult.error(580,"更新失败",null);
    }
    @GetMapping("/getAnchorAndLiveRoom")
    public Object getAnchorAndLiveRoom(String anchorId,String email){
        Anchor anchor= anchorService.findOneById(anchorId);
        System.out.println(anchor);
        if(anchor==null){
            System.out.println(111111);
            return ResponseResult.error(520,"获取信息失败",null);
        }
        Map<Object, Object> map = anchorService.getAnchorAndLiveRoom(anchor,email);
        System.out.println(map);
        if(map!=null){
            return ResponseResult.success(map);
        }
        return ResponseResult.error(520,"获取信息失败",null);
    }
    @GetMapping("/focus")
    public Object focus(String anchorId,String email,int flag){
        if(StringUtils.isEmpty(anchorId)||StringUtils.isEmpty(email)){
            return ResponseResult.error(560,"你没有权限",null);
        }else {
            //有权限操作
            Boolean b = liveRoomService.opConcernAndOpFocus(anchorId, email, flag);
            if(b){
                return ResponseResult.success(null);
            }else{
                return ResponseResult.error(560,"你没有权限",null);
            }
        }
    }
    @GetMapping("/userFocus")
    public Object userFocus(String email){
        if(!StringUtils.isEmpty(email)){
            List focusLiveRoom = liveRoomService.findFocusLiveRoom(email);
            return ResponseResult.success(focusLiveRoom);
        }
        return ResponseResult.error(560,"失败了",null);
    }
}
