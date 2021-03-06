package com.github.zhangkaitao.shiro.chapter14.dao;

import com.github.zhangkaitao.shiro.chapter14.entity.Permission;

/**
 * 权限DAO - 接口
 */
public interface PermissionDao {

    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);
}
