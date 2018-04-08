package com.github.zhangkaitao.shiro.chapter13.service;

import com.github.zhangkaitao.shiro.chapter13.entity.Permission;

/**
 * 权限service - 接口
 */
public interface PermissionService {
    Permission createPermission(Permission permission);
    void deletePermission(Long permissionId);
}
