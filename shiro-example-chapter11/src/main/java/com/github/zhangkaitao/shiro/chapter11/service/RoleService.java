package com.github.zhangkaitao.shiro.chapter11.service;

import com.github.zhangkaitao.shiro.chapter11.entity.Role;

/**
 * service层 - 角色接口
 */
public interface RoleService {

    /**
     * 创建角色
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:16
     */
    Role createRole(Role role);

    /**
     * 删除角色
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:17
     */
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
