package com.rimi.repository;

import com.rimi.model.UserFocus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFocusRepository extends JpaRepository<UserFocus,Integer> {
    public UserFocus findByUserId(String userId);
    public UserFocus findByAnchorId(String anchorId);
}
