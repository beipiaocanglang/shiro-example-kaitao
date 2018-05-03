package com.github.zhangkaitao.shiro.chapter15.service;

import com.github.zhangkaitao.shiro.chapter15.entity.Role;

/**
 * 角色接口
 * author : sunpanhu
 * createTime : 2018/5/3 下午1:10
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
