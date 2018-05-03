package com.github.zhangkaitao.shiro.chapter15.service;

import com.github.zhangkaitao.shiro.chapter15.dao.RoleDao;
import com.github.zhangkaitao.shiro.chapter15.entity.Role;

/**
 * 角色接口 实现类
 * author : sunpanhu
 * createTime : 2018/5/3 下午1:10
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    public void deleteRole(Long roleId) {
        roleDao.deleteRole(roleId);
    }

    /**
     * 添加角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    public void correlationPermissions(Long roleId, Long... permissionIds) {
        roleDao.correlationPermissions(roleId, permissionIds);
    }

    /**
     * 移除角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
        roleDao.uncorrelationPermissions(roleId, permissionIds);
    }
}
