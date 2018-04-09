package com.github.zhangkaitao.shiro.chapter16.service;

import com.github.zhangkaitao.shiro.chapter16.entity.User;

import java.util.List;
import java.util.Set;

/**
 * 用户service - 接口
 */
public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    User createUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 删除用户信息
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
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    User findOne(Long userId);

    /**
     * 无条件查询所有用户 - 用户管理
     * author : sunpanhu
     * createTime : 2018/4/9 下午12:17
     * @return
     */
    List<User> findAll();

    /**
     * 根据用户名查找用户
     * author : sunpanhu
     * createTime : 2018/4/9 下午12:24
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);
}
