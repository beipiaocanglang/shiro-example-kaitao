package com.github.zhangkaitao.shiro.chapter3.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 根据角色字符串来解析得到权限集合。
 * 如果用户拥有role1，那么就返回一个“menu:*”的权限。
 * author : sunpanhu
 * createTime : 2018/4/20 下午3:52
 */
public class MyRolePermissionResolver implements RolePermissionResolver {
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if("role1".equals(roleString)) {
            Permission permission = new WildcardPermission("menu:*");
            List<Permission> permissions = Arrays.asList(permission);
            return permissions;
        }
        return null;
    }
}
