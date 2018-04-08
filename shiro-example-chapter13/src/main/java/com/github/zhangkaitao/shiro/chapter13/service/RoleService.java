package com.github.zhangkaitao.shiro.chapter13.service;

import com.github.zhangkaitao.shiro.chapter13.entity.Role;

/**
 * 角色service - 接口
 */
public interface RoleService {
    Role createRole(Role role);

    void deleteRole(Long roleId);

    /**
     * 添加角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    void correlationPermissions(Long roleId, Long... permissionIds);

    /**
     * 移除角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
