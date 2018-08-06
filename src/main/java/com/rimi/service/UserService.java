package com.rimi.service;

import com.rimi.model.User;

public interface UserService {
    public boolean regist(User user);

    public User login(String email,String password);
}
