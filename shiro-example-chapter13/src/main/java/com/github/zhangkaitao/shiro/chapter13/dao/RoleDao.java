package com.github.zhangkaitao.shiro.chapter13.dao;

import com.github.zhangkaitao.shiro.chapter13.entity.Role;

/**
 * 角色dao - 接口
 */
public interface RoleDao {

    Role createRole(Role role);

    void deleteRole(Long roleId);

    void correlationPermissions(Long roleId, Long... permissionIds);

    void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
