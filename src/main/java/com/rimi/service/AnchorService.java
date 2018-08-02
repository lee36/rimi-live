package com.rimi.service;

import com.rimi.model.Anchor;

public interface AnchorService {
    public Anchor regist(Anchor anchor);
    public boolean saveAnchorAndCreateLiveRoom(Anchor anchor);
}
