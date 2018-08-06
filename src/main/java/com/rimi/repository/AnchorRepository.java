package com.rimi.repository;

import com.rimi.model.Anchor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
<<<<<<< HEAD
    /**
     * 通过昵称找到主播
     */
=======

>>>>>>> 038dbf7d6d28ce082c3ed0fda2fc776d321a123f
    public Anchor findByNickName(String nickName);
}
