package com.rimi.service.impl;

import com.rimi.model.UserFocus;
import com.rimi.repository.UserFocusRepository;
import com.rimi.service.UserFocusService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserFocusServiceImpl implements UserFocusService {
    @Autowired
    private UserFocusRepository userFocusRepository;
    @Override
    public Boolean inferFocus(String userId) {
        List<UserFocus> exit = userFocusRepository.findByUserId(userId);
        if(exit.size()<=0){
            return false;
        }
        return true;
    }

    @Override
    public Boolean addFocus(UserFocus userFocus) {
        UserFocus save = userFocusRepository.save(userFocus);
        if(save!=null){
            return true;
        }
        return false;
    }
}
