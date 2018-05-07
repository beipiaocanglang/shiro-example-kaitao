package com.github.zhangkaitao.shiro.chapter17.service;

import com.github.zhangkaitao.shiro.chapter17.dao.UserDao;
import com.github.zhangkaitao.shiro.chapter17.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户端 SERVICE 接口实现类
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:14
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 创建用户
     * @param user
     */
    public User createUser(User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        User resultUser = userDao.createUser(user);
        return resultUser;
    }
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public User updateUser(User user) {
        User resultUser = userDao.updateUser(user);
        return resultUser;
    }
    /**
     * 删除用户
     * @param userId
     */
    public void deleteUser(Long userId) {
        userDao.deleteUser(userId);
    }
    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword) {
        User user =userDao.findOne(userId);
        user.setPassword(newPassword);

        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }
    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    public User findOne(Long userId) {
        User user = userDao.findOne(userId);
        return user;
    }
    /**
     * 查询所有用户列表
     * @return
     */
    public List<User> findAll() {
        List<User> userList = userDao.findAll();
        return userList;
    }
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }
}
