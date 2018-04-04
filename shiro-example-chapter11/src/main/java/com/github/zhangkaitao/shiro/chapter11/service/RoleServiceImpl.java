package com.github.zhangkaitao.shiro.chapter11.service;

import com.github.zhangkaitao.shiro.chapter11.dao.RoleDao;
import com.github.zhangkaitao.shiro.chapter11.dao.RoleDaoImpl;
import com.github.zhangkaitao.shiro.chapter11.entity.Role;

/**
 * service层 - 角色接口实现类
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao = new RoleDaoImpl();

    /**
     * 创建角色
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:16
     */
    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    /**
     * 删除角色
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:17
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