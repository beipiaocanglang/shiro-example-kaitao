package com.github.zhangkaitao.shiro.chapter11.dao;

import com.github.zhangkaitao.shiro.chapter11.entity.Role;

/**
 * Dao层 - 角色接口
 */
public interface RoleDao {

    Role createRole(Role role);

    void deleteRole(Long roleId);

    void correlationPermissions(Long roleId, Long... permissionIds);

    void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
