package com.github.zhangkaitao.shiro.chapter12.service;

import com.github.zhangkaitao.shiro.chapter12.entity.Permission;

/**
 * Service层 - 权限接口
 */
public interface PermissionService {
    /**
     * 创建权限
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:21
     */
    Permission createPermission(Permission permission);
    /**
     * 删除权限
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:21
     */
    void deletePermission(Long permissionId);
}
