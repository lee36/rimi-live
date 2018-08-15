package com.rimi.service;

import com.rimi.model.UserFocus;

public interface UserFocusService {
    public Boolean inferFocus(String userId);
    public Boolean addFocus(UserFocus userFocus);
}
