package com.github.zhangkaitao.shiro.chapter13.service;

import com.github.zhangkaitao.shiro.chapter13.dao.PermissionDao;
import com.github.zhangkaitao.shiro.chapter13.entity.Permission;

/**
 * 权限service - 接口实现类
 */
public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao;

    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}
