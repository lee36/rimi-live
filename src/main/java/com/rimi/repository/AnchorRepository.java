package com.rimi.repository;

import com.rimi.model.Anchor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnchorRepository extends JpaRepository<Anchor,String> {
    /**
     * 通过邮箱查找主播
     */
    public Anchor findByEmail(String email);
    /**
     * 通过手机号找主播
     */
    public Anchor findByPhoneNumber(String phoneNumber);
    /**
     * 通过昵称找到主播
     */
    public Anchor findByNickName(String nickName);
    /**
     * 通过直播间找到主博
     */
    public Anchor findByLiveNo(String liveNo);

    /**
     * 通过id找主播
     * */
    public Anchor findOneById(String id);

    public Page<Anchor> findByStatus(int status, Pageable pageable);
}
