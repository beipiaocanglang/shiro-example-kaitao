package com.github.zhangkaitao.shiro.chapter13.dao;

import com.github.zhangkaitao.shiro.chapter13.entity.Permission;

/**
 * 权限dao - 接口
 */
public interface PermissionDao {

    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);
}
