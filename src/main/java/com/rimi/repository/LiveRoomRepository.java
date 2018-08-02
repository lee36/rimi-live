package com.rimi.repository;

import com.rimi.model.LiveRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveRoomRepository extends JpaRepository<LiveRoom,String> {
}
