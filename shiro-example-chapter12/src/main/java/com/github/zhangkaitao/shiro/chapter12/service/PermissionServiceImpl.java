package com.github.zhangkaitao.shiro.chapter12.service;

import com.github.zhangkaitao.shiro.chapter12.dao.PermissionDao;
import com.github.zhangkaitao.shiro.chapter12.entity.Permission;

/**
 * Service层 - 权限接口实现类
 */
public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao;

    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    /**
     * 创建权限
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:22
     */
    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }
    /**
     * 删除权限
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:22
     */
    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}
