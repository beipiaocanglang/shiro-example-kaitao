package com.github.zhangkaitao.shiro.chapter14.dao;

import com.github.zhangkaitao.shiro.chapter14.entity.Role;

/**
 * 角色DAO - 接口
 */
public interface RoleDao {

    Role createRole(Role role);

    void deleteRole(Long roleId);

    void correlationPermissions(Long roleId, Long... permissionIds);

    void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
