package com.github.zhangkaitao.shiro.chapter23.dao;

import com.github.zhangkaitao.shiro.chapter23.entity.User;

import java.util.List;

/**
 * 用户 dao - 接口
 * author : sunpanhu
 * createTime : 2018/4/18 下午2:08
 */
public interface UserDao {

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    User findOne(Long userId);

    List<User> findAll();

    User findByUsername(String username);
}
