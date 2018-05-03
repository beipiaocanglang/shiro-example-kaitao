package com.github.zhangkaitao.shiro.chapter15.service;

import com.github.zhangkaitao.shiro.chapter15.entity.Permission;

/**
 * 权限接口
 * author : sunpanhu
 * createTime : 2018/5/3 下午1:11
 */
public interface PermissionService {

    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);
}
