package com.github.zhangkaitao.shiro.chapter15.dao;

import com.github.zhangkaitao.shiro.chapter15.entity.Permission;

/**
 * 权限DAO接口
 * author : sunpanhu
 * createTime : 2018/5/3 下午1:15
 */
public interface PermissionDao {

    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);
}
