package com.rimi.repository;

import com.rimi.model.Manager;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Manager,Integer> {
    public Manager findByUsername(String username);
}
