package com.github.zhangkaitao.shiro.chapter3.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * Permission接口提供了boolean implies(Permission p)方法用于判断权限匹配的
 * 并根据权限字符串是否以“+”开头来解析权限字符串为BitPermission或WildcardPermission。
 * author : sunpanhu
 * createTime : 2018/4/20 下午3:53
 */
public class BitAndWildPermissionResolver implements PermissionResolver {

    public Permission resolvePermission(String permissionString) {
        if(permissionString.startsWith("+")) {
            return new BitPermission(permissionString);
        }
        WildcardPermission wildcardPermission = new WildcardPermission(permissionString);
        return wildcardPermission;
    }
}
