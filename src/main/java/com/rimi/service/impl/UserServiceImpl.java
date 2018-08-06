package com.rimi.service.impl;

import com.rimi.componet.IdGenneratorComponet;
import com.rimi.constant.UserConstant;
import com.rimi.model.User;
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
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user==null) {
            return null;
        }
        // 获取密码并比较
        String db = DigestUtils.md5Hex(password + UserConstant.SALT);
        if(db.equals(user.getPassword())){
            return user;
        }
        return null;
    }
}
