package com.github.zhangkaitao.shiro.chapter17.dao;

import com.github.zhangkaitao.shiro.chapter17.entity.User;

import java.util.List;

/**
 * 用户端 DAO 接口
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:12
 */
public interface UserDao {
    /**
     * 创建用户
     * @param user
     */
    User createUser(User user);
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    User updateUser(User user);
    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(Long userId);
    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    User findOne(Long userId);
    /**
     * 查询所有用户列表
     * @return
     */
    List<User> findAll();
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
