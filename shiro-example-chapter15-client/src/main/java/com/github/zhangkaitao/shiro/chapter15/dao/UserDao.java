package com.github.zhangkaitao.shiro.chapter15.dao;

import com.github.zhangkaitao.shiro.chapter15.entity.User;

import java.util.Set;

/**
 * 用户DAO接口
 * author : sunpanhu
 * createTime : 2018/5/3 下午1:14
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
