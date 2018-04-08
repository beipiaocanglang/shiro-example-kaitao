package com.github.zhangkaitao.shiro.chapter14.dao;

import com.github.zhangkaitao.shiro.chapter14.entity.User;

import java.util.Set;

/**
 * 用户DAO - 接口
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
