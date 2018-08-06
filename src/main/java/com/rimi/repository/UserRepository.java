<<<<<<< HEAD
package com.rimi.repository;

import com.rimi.model.Manager;
import com.rimi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * 通过邮箱查找用户
     */
    public User findByEmail(String email);
    /**
     * 通过昵称找到用户
     */
    public User findByNickName(String nickName);

}
=======
package com.rimi.repository;

import com.rimi.model.Manager;
import com.rimi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * 通过邮箱查找用户
     */
    public User findByEmail(String email);
    /**
     * 通过昵称找到用户
     */
    public User findByNickName(String nickName);

}
>>>>>>> 038dbf7d6d28ce082c3ed0fda2fc776d321a123f
