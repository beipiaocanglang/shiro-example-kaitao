package com.github.zhangkaitao.shiro.chapter12.service;

import com.github.zhangkaitao.shiro.chapter12.dao.RoleDao;
import com.github.zhangkaitao.shiro.chapter12.entity.Role;

/**
 * Service层 - 角色接口实现类
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /**
     * 添加角色
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:24
     */
    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }
    /**
     * 删除角色
     * author : sunpanhu
     * createTime : 2018/4/4 下午4:25
     */
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
