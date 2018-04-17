package com.github.zhangkaitao.shiro.chapter19.dao;

import com.github.zhangkaitao.shiro.chapter19.entity.Role;
import org.apache.shiro.authz.permission.RolePermissionResolver;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface RoleDao {
    Role createRole(Role role);

    Role updateRole(Role role);

    void deleteRole(Long roleId);

    Role findOne(Long roleId);

    List<Role> findAll();
}
