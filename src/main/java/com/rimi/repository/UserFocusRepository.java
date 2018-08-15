package com.rimi.repository;

import com.rimi.model.UserFocus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFocusRepository extends JpaRepository<UserFocus,Integer> {
    public List<UserFocus> findByUserId(String email);
    public Integer deleteByUserIdAndAnchorId(String userId,String anchorId);
}
