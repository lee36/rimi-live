package com.rimi.controller;

import com.rimi.model.LiveRoom;
import com.rimi.model.Type;
import com.rimi.service.LiveRoomService;
import com.rimi.service.TypeService;
import com.rimi.vo.LiveRoomVo;
import com.rimi.vo.ResponseResult;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/liveroom")
public class LiveRoomController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private LiveRoomService liveRoomService;

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
}
