package com.rimi.service.impl;

import com.rimi.componet.IdGenneratorComponet;
import com.rimi.constant.UserConstant;
import com.rimi.form.UpdateUserForm;
import com.rimi.model.Anchor;
import com.rimi.model.User;
import com.rimi.repository.AnchorRepository;
import com.rimi.repository.UserRepository;
import com.rimi.service.UserService;
import com.rimi.vo.ResponseResult;
import com.sun.mail.smtp.DigestMD5;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnchorRepository anchorRepository;
    //获取ID生成器
    @Autowired
    private IdGenneratorComponet idGenneratorComponet;

    @Override
    @Transactional
    public boolean regist(User user) {
        String pwd = DigestUtils.md5Hex(user.getPassword() + UserConstant.SALT);
        user.setPassword(pwd);
        // 保存到数据库
        User save = userRepository.save(user);
        if (save == null){
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Object login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user==null){
            Anchor anchor = anchorRepository.findByEmail(email);
            if(anchor==null){
                return null;
            }else{
                //在主播里找到
                String md5Pass = DigestUtils.md5Hex(password + UserConstant.SALT);
                if(md5Pass.equals(anchor.getPassword())){
                    return anchor;
                }else{
                    return null;
                }
            }
        }else{
            //在用户里找到
            //在主播里找到
            String md5Pass = DigestUtils.md5Hex(password + UserConstant.SALT);
            if(md5Pass.equals(user.getPassword())){
                return user;
            }else{
                return null;
            }
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean updateUser(String id, UpdateUserForm userForm) {
        User one = userRepository.findOneById(id);
        if (one==null){
            return false;
        }
        String formPass = userForm.getPassword();
        String dbPass = DigestUtils.md5Hex(formPass + UserConstant.SALT);
        one.setNickName(userForm.getNickName());
        one.setPassword(dbPass);
        userRepository.save(one);
        return true;
    }

    @Override
    public boolean updateUserImg(String id,String filename) {
        User one = userRepository.findOneById(id);
        if (one==null){
            return false;
        }
        one.setHeadImg(filename);
        userRepository.save(one);
        return true;
    }
}
