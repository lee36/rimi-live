package com.rimi.service;

import com.rimi.model.Anchor;
import com.rimi.model.Manager;
import com.rimi.model.User;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    public Manager AdminLogin(Manager manager);

    Page<User> listAllUser(Pageable pageable);

    public Boolean deleteUser(String id);

    public Boolean deleteAnchor(String id);

    public Boolean banUser(String id);

    public Boolean banAnchor(String id);

    public boolean freeUser(String id);

    public boolean freeAnchor(String id);

    Page<Anchor> listAllAnchor(Pageable pageable);

    public Page<User> findBanUser(int status, Pageable pageable);

    public Page<Anchor> findBanAnchor(int status,Pageable pageable);


}
