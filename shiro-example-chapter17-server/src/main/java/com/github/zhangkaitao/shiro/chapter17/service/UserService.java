package com.github.zhangkaitao.shiro.chapter17.service;

import com.github.zhangkaitao.shiro.chapter17.entity.User;

import java.util.List;

/**
 * 用户端 SERVICE 接口
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:14
 */
public interface UserService {
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
     * 修改密码
     * @param userId
     * @param newPassword
     */
    void changePassword(Long userId, String newPassword);
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
