package com.rimi.repository;

import com.rimi.model.LiveRoom;
import com.rimi.vo.LiveRoomVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveRoomRepository extends JpaRepository<LiveRoom,String> {

    public List<LiveRoom> findByTypeAndStatus(Integer id, Integer status);
}
