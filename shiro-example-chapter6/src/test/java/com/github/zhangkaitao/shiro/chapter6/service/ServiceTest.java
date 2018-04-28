package com.github.zhangkaitao.shiro.chapter6.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.zhangkaitao.shiro.chapter6.BaseTest;
import com.github.zhangkaitao.shiro.chapter6.entity.LuoPan;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * service层的测试案例
 */
public class ServiceTest extends BaseTest {

    private LuoPanService luoPanService = new LuoPanServiceImpl();

    @Test
    public void findAll(){
        List<LuoPan> all = luoPanService.findAll();

        System.out.println(JSONUtils.toJSONString(all.get(0)));

    }

    @Test
    public void testUserRolePermissionRelation() {
        //根据用户名zhang查询用户对应的角色
        Set<String> roles = userService.findRoles(u1.getUsername());
        Assert.assertEquals(1, roles.size());
        Assert.assertTrue(roles.contains(r1.getRole()));

        //根据用户查询用户对应的权限
        Set<String> permissions = userService.findPermissions(u1.getUsername());
        Assert.assertEquals(3, permissions.size());
        Assert.assertTrue(permissions.contains(p3.getPermission()));

        //li
        roles = userService.findRoles(u2.getUsername());
        Assert.assertEquals(0, roles.size());
        permissions = userService.findPermissions(u2.getUsername());
        Assert.assertEquals(0, permissions.size());

        //解除 admin-menu:update关联
        roleService.uncorrelationPermissions(r1.getId(), p3.getId());
        permissions = userService.findPermissions(u1.getUsername());
        Assert.assertEquals(2, permissions.size());
        Assert.assertFalse(permissions.contains(p3.getPermission()));

        //删除一个permission
        permissionService.deletePermission(p2.getId());
        permissions = userService.findPermissions(u1.getUsername());
        Assert.assertEquals(1, permissions.size());

        //解除 zhang-admin关联
        userService.uncorrelationRoles(u1.getId(), r1.getId());
        roles = userService.findRoles(u1.getUsername());
        Assert.assertEquals(0, roles.size());
    }
}
