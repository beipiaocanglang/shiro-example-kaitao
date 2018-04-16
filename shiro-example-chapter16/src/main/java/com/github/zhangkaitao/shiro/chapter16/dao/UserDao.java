package com.github.zhangkaitao.shiro.chapter16.dao;

import com.github.zhangkaitao.shiro.chapter16.entity.User;

import java.util.List;

/**
 * 用户DAO - 接口
 */
public interface UserDao {
    /**
     * 新增用户
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:36
     * @return
     */
    User createUser(User user);
    /**
     * 修改用户信息
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:37
     * @return
     */
    User updateUser(User user);
    /**
     * 删除用户
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:37
     * @return
     */
    void deleteUser(Long userId);
    /**
     * 根据用户id 查询用户信息
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:37
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
     * 根据用户名查询用户信息
     * author : sunpanhu
     * createTime : 2018/4/9 下午12:24
     * @param username
     * @return
     */
    User findByUsername(String username);
}
