package com.rimi.controller;

import com.rimi.componet.BuilderErrorComponet;
import com.rimi.componet.BuilderPageComponet;
import com.rimi.model.Anchor;
import com.rimi.model.Manager;
import com.rimi.model.User;
import com.rimi.repository.UserRepository;
import com.rimi.service.AdminService;
import com.rimi.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 管理员的控制层
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Object login(@Valid Manager manager, BindingResult result){
        System.out.println(11111);
        if(result.hasErrors()){
            HashMap errors = BuilderErrorComponet.builderError(result);
            return ResponseResult.error(550,"登录失败",errors);
        }
        Manager exits = adminService.AdminLogin(manager);
        if(exits==null){
            return ResponseResult.error(560,"登录失败",null);
        }
        return ResponseResult.success(exits);
    }

    /**
     * 加载用户
     * @return
     */
    @GetMapping(value = "/loadUser",produces ="application/json;charset=UTF-8" )
    public Object OpinitUser(@PageableDefault(page = 0,size = 5) Pageable pageable,String username
            ,String password){
           System.out.println(pageable);
           Page<User>  page = adminService.listAllUser(pageable);
           HashMap map = BuilderPageComponet.builder(page);
           return ResponseResult.success(map);
    }

    @GetMapping("/deleteUser")
    public Object OpdeleteUser(@RequestParam(required = true) String id,String username
            ,String password){
        Boolean b=null;
        if(id==null){
            return ResponseResult.error(510,"请输入要删除的用户id",null);
        }
        if(id.startsWith("a")){
            b = adminService.deleteAnchor(id);
        }else {
            b = adminService.deleteUser(id);
        }
        if(b){
            return ResponseResult.success(true);
        }
        return ResponseResult.error(520,"删除失败",null);
    }
    /**
     * 禁言
     */
    @GetMapping("/banUser")
    public Object OpbanUser(@RequestParam(required = true) String id,String username
            ,String password){
        Boolean b=null;
        if(id==null){
            return ResponseResult.error(510,"请输入你要禁言的用户id",null);
        }
        if(id.startsWith("a")){
            b = adminService.banAnchor(id);
        }else {
            b = adminService.banUser(id);
        }
        if(b){
            return ResponseResult.success(true);
        }
        return ResponseResult.error(520,"封禁失败",null);
    }
    /**
     * 主播列表
     * @param pageable
     * @param manager
     * @param result
     * @return
     */
    @GetMapping(value = "/loadAnchor",produces ="application/json;charset=UTF-8" )
    public Object OpinitAnchor(@PageableDefault(page = 0,size = 5) Pageable pageable,String username
            ,String password){
        System.out.println(pageable);
        Page<Anchor>  page = adminService.listAllAnchor(pageable);
        HashMap map = BuilderPageComponet.builder(page);
        return ResponseResult.success(map);
    }

    /**
     * 加载封禁用户
     * @param pageable
     * @param manager
     * @param result
     * @return
     */
    @GetMapping("/loadBanUser")
    public Object OpinitBandUser(@PageableDefault(page = 0,size = 2) Pageable pageable,String username
            ,String password){
        //构建map对象
        int realPage=0;
        List<HashMap<Object, Object>> all = new ArrayList<>();
        //先找到users
        Page<User> banUser = adminService.findBanUser(3, pageable);
        //获取页数
        int userTotalPages = banUser.getTotalPages();
        for (User user : banUser) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("id",user.getId());
            map.put("nickName",user.getNickName());
            map.put("email",user.getEmail());
            all.add(map);
        }
        //先找到anchors
        Page<Anchor> banAnchor = adminService.findBanAnchor(3, pageable);
        //获取页数
        int anchorTotalPages = banAnchor.getTotalPages();
        for (Anchor anchor : banAnchor) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("id",anchor.getId());
            map.put("nickName",anchor.getNickName());
            map.put("email",anchor.getEmail());
            all.add(map);
        }
        //实际页数
        if(userTotalPages>anchorTotalPages){
            realPage=userTotalPages;
        }else{
            realPage=anchorTotalPages;
        }
        HashMap resultMap=new HashMap<>();
        resultMap.put("totalPages",realPage);
        resultMap.put("pageSize",pageable.getPageSize());
        resultMap.put("currentPage",pageable.getPageNumber());
        resultMap.put("lists",all);
        return ResponseResult.success(resultMap);
    }
    /**
     * 解封
     */
    @GetMapping("/freeUser")
    public Object OpFreeUser(@RequestParam(required = true) String id,String username
            ,String password){
        Boolean b=null;
        if(id==null){
            return ResponseResult.error(510,"请输入你要解封的用户id",null);
        }
        if(id.startsWith("a")){
            System.out.println(123);
            b = adminService.freeAnchor(id);
        }else {
            b = adminService.freeUser(id);
        }
        System.out.println(b);
        if(b){
            return ResponseResult.success(true);
        }
        return ResponseResult.error(520,"封禁失败",null);
    }

}
