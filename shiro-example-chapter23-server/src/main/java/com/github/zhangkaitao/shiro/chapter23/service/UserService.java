package com.github.zhangkaitao.shiro.chapter23.service;

import com.github.zhangkaitao.shiro.chapter23.entity.User;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserService {
    /**
     * 创建用户
     * @param user
     */
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    void changePassword(Long userId, String newPassword);


    User findOne(Long userId);

    List<User> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
