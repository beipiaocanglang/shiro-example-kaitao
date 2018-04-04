package com.github.zhangkaitao.shiro.chapter12.service;

import com.github.zhangkaitao.shiro.chapter12.entity.User;

import java.util.Set;

/**
 * Service层 - 角色接口
 */
public interface UserService {
    /**
     * 创建用户
     * @param user
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     */
    User createUser(User user);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     */
    void changePassword(Long userId, String newPassword);

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     */
    void correlationRoles(Long userId, Long... roleIds);


    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     */
    void uncorrelationRoles(Long userId, Long... roleIds);

    /**
     * 根据用户名查找用户
     * @param username
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     * @return
     */
    Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     * @return
     */
    Set<String> findPermissions(String username);

}
