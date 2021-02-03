package org.example.blog.dao;

import org.example.blog.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    /**
     * 修改用户状态
     * 通过修改用户的状态来删除用户
     * @param userId
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_user` set `state` = '0' where `id` = ?")
    int deleteUserByState(String userId);

    /**
     * 不查询密码
     * @param pageable
     * @return
     */
    @Query(value = "select new User(u.id,u.userName,u.roles,u.avatar,u.email,u.sign,u.state,u.regIp,u.loginIp,u.createTime,u.updateTime) from User as u")
    Page<User> listAllUserNoPassword(Pageable pageable);

    /**
     * 修改密码
     * @param encode
     * @param email
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_user` set `password` = ? where `email` = ?")
    int updatePasswordByEmail(String encode, String email);

    /**
     * 修改邮箱
     * @param email
     * @param id
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_user` set `email` = ? where `id` = ?")
    int updateEmailById(String email, String id);

}
