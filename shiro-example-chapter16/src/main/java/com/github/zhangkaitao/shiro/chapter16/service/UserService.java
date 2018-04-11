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
     *
     * 获取该用户的权限、角色、资源以及资源对应的菜单
     * 查询流程：
     *      使用自定义注解 @CurrentUser User loginUser  可以拿到用户信息
     *      1、根据用户名查找用户信息
     *          user = null：返回null
     *      2、用户信息不为null时 循环用户信息中的 多个角色id 分别查询对应的角色信息
     *      3、将查到的多个角色信息中对应的多个资源id封装成一个set集合
     *      4、将资源集合作为参数查询资源表(循环查询)，获取对应的权限封装成set集合返回
     *      5、根据权限集合查询对应的菜单
     *          5.1、获取所有资源
     *          5.2、循环资源 不是根节点、类型不是菜单的添加到封装的集合，
     *          5.3、循环权限集合和资源对应的集合对比 相等就添加到自定义的集合中
     *      6、放入Model中
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);
}
