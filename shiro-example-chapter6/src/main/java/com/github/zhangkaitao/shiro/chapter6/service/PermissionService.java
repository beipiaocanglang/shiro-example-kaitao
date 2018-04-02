package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.entity.Permission;

/**
 * service层 - 权限接口
 */
public interface PermissionService {
    /**
     * 创建权限
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:15
     */
    Permission createPermission(Permission permission);
    /**
     * 删除权限
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:15
     */
    void deletePermission(Long permissionId);
}
