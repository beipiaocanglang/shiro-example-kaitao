package com.github.zhangkaitao.shiro.chapter12.dao;

import com.github.zhangkaitao.shiro.chapter12.entity.User;

import java.util.Set;

/**
 * DAO层 - 用户接口
 */
public interface UserDao {
    /**
     * 创建用户
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:53
     */
    User createUser(User user);
    /**
     * 更新用户
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:53
     */
    void updateUser(User user);
    /**
     * 删除用户
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:53
     */
    void deleteUser(Long userId);

    /**
     * 添加用户 和 角色 的对应关系
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:53
     */
    void correlationRoles(Long userId, Long... roleIds);
    /**
     * 解除用户 和 角色 的对应关心
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:53
     */
    void uncorrelationRoles(Long userId, Long... roleIds);

    /**
     * 根据用户ID查询用户信息
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:53
     */
    User findOne(Long userId);
    /**
     * 根据用户名查询用户信息
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:53
     */
    User findByUsername(String username);
    /**
     * 根据用户名查询用户对应的角色信息
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:53
     */
    Set<String> findRoles(String username);
    /**
     * 根据用户名查询用户对应 权限信息
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:53
     */
    Set<String> findPermissions(String username);
}
