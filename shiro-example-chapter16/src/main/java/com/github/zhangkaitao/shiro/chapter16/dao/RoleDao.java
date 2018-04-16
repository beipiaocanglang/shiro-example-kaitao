package com.github.zhangkaitao.shiro.chapter16.dao;

import com.github.zhangkaitao.shiro.chapter16.entity.Role;

import java.util.List;

/**
 * 角色DAO - 接口
 */
public interface RoleDao {

    /**
     * 创建角色
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:53
     * @return
     */
    Role createRole(Role role);

    /**
     * 修改角色
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:53
     * @return
     */
    Role updateRole(Role role);

    /**
     * 根据角色id删除角色信息
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:53
     * @return
     */
    void deleteRole(Long roleId);

    /**
     * 根据角色id查询角色信息
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:53
     * @return
     */
    Role findOne(Long roleId);

    /**
     * 查询所有角色
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:52
     * @return
     */
    List<Role> findAll();
}
