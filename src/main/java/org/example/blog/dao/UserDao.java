package org.example.blog.dao;

import org.example.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查找
     *
     * @param userName
     * @return
     */
    User findOneByUserName(String userName);

    /**
     * 根据邮箱查找
     *
     * @param emailAddress
     * @return
     */
    User findOneByEmail(String emailAddress);

    /**
     * 根据用户Id查找
     * @param userId
     */
    User findOneById(String userId);
}
