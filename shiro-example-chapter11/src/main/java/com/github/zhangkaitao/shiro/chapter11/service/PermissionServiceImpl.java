package com.github.zhangkaitao.shiro.chapter11.service;

import com.github.zhangkaitao.shiro.chapter11.dao.PermissionDao;
import com.github.zhangkaitao.shiro.chapter11.dao.PermissionDaoImpl;
import com.github.zhangkaitao.shiro.chapter11.entity.Permission;

/**
 * service层 - 权限接口实现类
 */
public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao = new PermissionDaoImpl();

    /**
     * 创建权限
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:15
     */
    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    /**
     * 删除权限
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:15
     */
    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}
