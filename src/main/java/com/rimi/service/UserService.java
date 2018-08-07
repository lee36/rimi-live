package com.rimi.service;

import com.rimi.model.User;

public interface UserService {
    public boolean regist(User user);

    public Object login(String email,String password);

    public User findByEmail(String email);
}
