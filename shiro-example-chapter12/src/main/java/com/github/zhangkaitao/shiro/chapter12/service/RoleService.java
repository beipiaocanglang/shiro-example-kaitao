package com.github.zhangkaitao.shiro.chapter12.service;

import com.github.zhangkaitao.shiro.chapter12.entity.Role;

/**
 * Service层 - 角色接口
 */
public interface RoleService {
    /**
     * 创建角色
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:23
     */
    Role createRole(Role role);
    /**
     * 删除角色
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:23
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
