package com.rimi.service;

import com.rimi.form.UpdateUserForm;
import com.rimi.model.User;

public interface UserService {
    public boolean regist(User user);

    public Object login(String email,String password);

    public User findByEmail(String email);

    boolean updateUser(String id, UpdateUserForm userForm);

    boolean updateUserImg(String id,String filename);
}
