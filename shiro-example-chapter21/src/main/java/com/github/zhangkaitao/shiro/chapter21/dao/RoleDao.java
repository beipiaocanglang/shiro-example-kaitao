package com.github.zhangkaitao.shiro.chapter21.dao;

import com.github.zhangkaitao.shiro.chapter21.entity.Role;

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
