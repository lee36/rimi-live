package com.rimi.service.impl;

import com.rimi.model.UserFocus;
import com.rimi.repository.UserFocusRepository;
import com.rimi.service.UserFocusService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFocusServiceImpl implements UserFocusService {
    @Autowired
    private UserFocusRepository userFocusRepository;
    @Override
    public Boolean inferFocus(String userId) {
        UserFocus exit = userFocusRepository.findByUserId(userId);
        if(exit==null){
            return false;
        }
        return true;
    }
}
