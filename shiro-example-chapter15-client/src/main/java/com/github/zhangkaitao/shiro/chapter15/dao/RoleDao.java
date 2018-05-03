package com.github.zhangkaitao.shiro.chapter15.dao;

import com.github.zhangkaitao.shiro.chapter15.entity.Role;

/**
 * 角色DAO接口
 * author : sunpanhu
 * createTime : 2018/5/3 下午1:15
 */
public interface RoleDao {

    Role createRole(Role role);

    void deleteRole(Long roleId);

    void correlationPermissions(Long roleId, Long... permissionIds);

    void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
