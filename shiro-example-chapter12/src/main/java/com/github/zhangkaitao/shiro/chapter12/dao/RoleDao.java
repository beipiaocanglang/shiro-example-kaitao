package com.github.zhangkaitao.shiro.chapter12.dao;

import com.github.zhangkaitao.shiro.chapter12.entity.Role;

/**
 * DAO层 - 角色接口
 */
public interface RoleDao {
    /**
     * 创建角色
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:50
     */
    Role createRole(Role role);
    /**
     * 删除角色
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:50
     */
    void deleteRole(Long roleId);

    /**
     * 添加角色 与 权限的关系
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:50
     */
    void correlationPermissions(Long roleId, Long... permissionIds);
    /**
     * 删除角色 与 权限的关系
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:50
     */
    void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
