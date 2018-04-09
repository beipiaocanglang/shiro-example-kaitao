package com.github.zhangkaitao.shiro.chapter16.dao;

import com.github.zhangkaitao.shiro.chapter16.entity.User;

import java.util.List;

/**
 * 用户DAO - 接口
 */
public interface UserDao {

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    User findOne(Long userId);

    /**
     * 无条件查询所有用户 - 用户管理
     * author : sunpanhu
     * createTime : 2018/4/9 下午12:17
     * @return
     */
    List<User> findAll();

    /**
     * 根据用户名查询用户信息
     * author : sunpanhu
     * createTime : 2018/4/9 下午12:24
     * @param username
     * @return
     */
    User findByUsername(String username);
}
