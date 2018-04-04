package com.github.zhangkaitao.shiro.chapter12.dao;

import com.github.zhangkaitao.shiro.chapter12.entity.Permission;

/**
 * DAO层 - 权限接口
 */
public interface PermissionDao {

    /**
     * 创建权限
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:47
     */
    Permission createPermission(Permission permission);

    /**
     * 删除权限
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:48
     */
    void deletePermission(Long permissionId);
}
