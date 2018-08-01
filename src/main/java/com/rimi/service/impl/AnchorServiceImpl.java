package com.rimi.service.impl;

import com.rimi.model.Anchor;
import com.rimi.repository.AnchorRepository;
import com.rimi.service.AnchorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnchorServiceImpl implements AnchorService {
    @Autowired
    private AnchorRepository anchorRepository;
    @Override
    public Anchor regist(Anchor anchor) {
        Anchor save = anchorRepository.save(anchor);
        if(save==null){
            return null;
        }
        return save;
    }
}
