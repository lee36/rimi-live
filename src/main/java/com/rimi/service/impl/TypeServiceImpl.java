package com.rimi.service.impl;

import com.rimi.model.Type;
import com.rimi.repository.TypeRepository;
import com.rimi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Override
    public List<Type> findAllType() {
        return typeRepository.findAll();
    }
}
