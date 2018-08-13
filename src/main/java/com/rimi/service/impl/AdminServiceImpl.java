package com.rimi.service.impl;

import com.rimi.constant.AdminConstant;
import com.rimi.constant.UserConstant;
import com.rimi.model.Anchor;
import com.rimi.model.Manager;
import com.rimi.model.User;
import com.rimi.repository.AdminRepository;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.UserRepository;
import com.rimi.service.AdminService;
import org.apache.commons.codec.digest.DigestUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnchorRepository anchorRepository;
    @Override
    public Manager AdminLogin(Manager login) {
        String password = login.getPassword();
        String username=login.getUsername();
        Manager manager = adminRepository.findByUsername(username);
        if(manager==null){
            return null;
        }
        //获取是数据库密码
        String dbPass = manager.getPassword();
        //讲表单密码加密比较
        String md5Pass = DigestUtils.md5Hex(password + AdminConstant.SALT);
        if(dbPass.equals(md5Pass)){
            return manager;
        }
        return null;
    }

    /**
     * 分页
     * @param pageable
     * @return
     */
    @Override
    public Page<User> listAllUser(Pageable pageable) {
         return userRepository.findAll(pageable);
    }

    @Override
    public Boolean deleteUser(String id) {
        if(id!=null){
            try{
              userRepository.deleteById(id);
              return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public Boolean deleteAnchor(String id) {
        try {
            anchorRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean banUser(String id) {
        if(id!=null){
            User user = userRepository.getOne(id);
            //设置状态
            user.setStatus(3);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean banAnchor(String id) {
        if(id!=null){
            Anchor anchor= anchorRepository.getOne(id);
            //设置状态
            anchor.setStatus(3);
            anchorRepository.save(anchor);
            return true;
        }
        return false;
    }

    @Override
    public Page<Anchor> listAllAnchor(Pageable pageable) {
        return anchorRepository.findAll(pageable);
    }

    @Override
    public Page<User> findBanUser(int status, Pageable pageable) {
        return userRepository.findByStatus(status,pageable);
    }

    @Override
    public Page<Anchor> findBanAnchor(int status, Pageable pageable) {
        return anchorRepository.findByStatus(status,pageable);
    }

    @Override
    @Transactional
    public boolean freeUser(String id) {
        if(id!=null){
            User user = userRepository.getOne(id);
            //设置状态
            user.setStatus(1);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean freeAnchor(String id) {
        if(id!=null){
            Anchor anchor= anchorRepository.getOne(id);
            System.out.println(anchor+"===========");
            //设置状态
            anchor.setStatus(1);
            anchorRepository.save(anchor);
            return true;
        }
        System.out.println("hahha");
        return false;
    }
}
