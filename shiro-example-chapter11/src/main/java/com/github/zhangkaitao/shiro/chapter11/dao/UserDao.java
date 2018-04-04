package com.github.zhangkaitao.shiro.chapter11.dao;

import com.github.zhangkaitao.shiro.chapter11.entity.User;

import java.util.Set;

/**
 * Dao层 - 用户接口
 */
public interface UserDao {

    User createUser(User user);

    void updateUser(User user);

    void deleteUser(Long userId);

    void correlationRoles(Long userId, Long... roleIds);

    void uncorrelationRoles(Long userId, Long... roleIds);

    User findOne(Long userId);

    User findByUsername(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
}
