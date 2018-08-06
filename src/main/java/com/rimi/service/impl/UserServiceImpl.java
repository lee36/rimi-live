package com.rimi.service.impl;

import com.rimi.componet.IdGenneratorComponet;
import com.rimi.model.User;
import com.rimi.repository.UserRepository;
import com.rimi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    //获取ID生成器
    @Autowired
    private IdGenneratorComponet idGenneratorComponet;

    @Override
    public boolean regist(User user) {
        User save = userRepository.save(user);
        if (save == null){
            return false;
        }
        return true;
    }
}
