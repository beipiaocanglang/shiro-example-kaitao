package com.github.zhangkaitao.shiro.chapter12.service;

import com.github.zhangkaitao.shiro.chapter12.dao.UserDao;
import com.github.zhangkaitao.shiro.chapter12.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service层 - 角色接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private PasswordHelper passwordHelper;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setPasswordHelper(PasswordHelper passwordHelper) {
        this.passwordHelper = passwordHelper;
    }

    /**
     * 创建用户
     * @param user
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     */
    public User createUser(User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        return userDao.createUser(user);
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     */
    public void changePassword(Long userId, String newPassword) {
        User user =userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     */
    public void correlationRoles(Long userId, Long... roleIds) {
        userDao.correlationRoles(userId, roleIds);
    }

    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     */
    public void uncorrelationRoles(Long userId, Long... roleIds) {
        userDao.uncorrelationRoles(userId, roleIds);
    }

    /**
     * 根据用户名查找用户
     * @param username
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     * @return
     */
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 根据用户名查找其角色
     * @param username
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     * @return
     */
    public Set<String> findRoles(String username) {
        return userDao.findRoles(username);
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:26
     * @return
     */
    public Set<String> findPermissions(String username) {
        return userDao.findPermissions(username);
    }
}
