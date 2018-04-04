package com.github.zhangkaitao.shiro.chapter11.dao;

import com.github.zhangkaitao.shiro.chapter11.entity.Permission;

/**
 * Dao层 - 权限接口
 */
public interface PermissionDao {

    /**
     * 创建权限
     * author : sunpanhu
     * createTime : 2018/4/4 下午2:25
     */
    Permission createPermission(Permission permission);

    /**
     * 删除权限
     * author : sunpanhu
     * createTime : 2018/4/4 下午2:25
     */
    void deletePermission(Long permissionId);
}
