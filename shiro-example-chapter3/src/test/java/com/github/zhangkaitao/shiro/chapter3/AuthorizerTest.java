package com.github.zhangkaitao.shiro.chapter3;

import junit.framework.Assert;
import org.junit.Test;

/**
 * 自定义授权测试案例
 * 因为不支持hasAnyRole/isPermittedAny这种方式的授权，
 * 可以参考我的一篇《简单shiro扩展实现NOT、AND、OR权限验证 》进行简单的扩展完成这个需求，
 * 在这篇文章中通过重写AuthorizingRealm里的验证逻辑实现的。
 * author : sunpanhu
 * createTime : 2018/4/20 下午3:49
 */
public class AuthorizerTest extends BaseTest {
    /**
     * 自定义授权
     * author : sunpanhu
     * createTime : 2018/3/27 下午6:18
     */
    @Test
    public void testIsPermitted() {
        login("classpath:shiro-authorizer.ini", "zhang", "123");
        //数据库数据
        //5	role1	+user1+10   10表示 新增、查看 权限
        //7	role1	+user2+10   10表示 新增、查看 权限
        //6	role1	user1:*
        //8	role1	user2:*

        //判断拥有权限：user:create
        Assert.assertTrue(subject().isPermitted("user1:update"));
        Assert.assertTrue(subject().isPermitted("user2:update"));
        //通过二进制位的方式表示权限
        Assert.assertTrue(subject().isPermitted("+user1+2"));//新增权限
        Assert.assertTrue(subject().isPermitted("+user1+8"));//查看权限
        Assert.assertTrue(subject().isPermitted("+user2+10"));//新增及查看

        Assert.assertFalse(subject().isPermitted("+user1+4"));//没有删除权限 只有2 和 8权限

        Assert.assertTrue(subject().isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
    }

    /**
     * 连接数据库 实现动态授权
     * author : sunpanhu
     * createTime : 2018/3/27 下午6:18
     */
    @Test
    public void testIsPermitted2() {
        login("classpath:shiro-jdbc-authorizer.ini", "zhang", "123");
        //数据库数据
        //5	role1	+user1+10   10表示 新增、查看 权限
        //7	role1	+user2+10   10表示 新增、查看 权限
        //6	role1	user1:*
        //8	role1	user2:*

        //判断拥有权限：user:create
        //Assert.assertTrue(subject().isPermitted("user1:update"));
        //Assert.assertTrue(subject().isPermitted("user2:update"));
        //通过二进制位的方式表示权限
        //Assert.assertTrue(subject().isPermitted("+user1+2"));//新增 权限
        //Assert.assertTrue(subject().isPermitted("+user1+8"));//查看 权限
        //Assert.assertTrue(subject().isPermitted("+user2+10"));//新增 及 查看 权限
        //Assert.assertFalse(subject().isPermitted("+user1+4"));//没有 删除权限
        //通过MyRolePermissionResolver解析得到的权限
        //Assert.assertTrue(subject().isPermitted("menu:view"));


        //判断拥有权限：user:create
        boolean permitted5 = subject().isPermitted("user1:update");
        boolean permitted6 = subject().isPermitted("user2:update");

        //通过二进制位的方式表示权限
        boolean permitted = subject().isPermitted("+user1+2");//新增 权限
        boolean permitted1 = subject().isPermitted("+user1+8");//查看 权限
        boolean permitted2 = subject().isPermitted("+user2+10");//新增 及 查看 权限
        boolean permitted3 = subject().isPermitted("+user1+4");//没有 删除权限

        //通过MyRolePermissionResolver解析得到的权限
        boolean permitted4 = subject().isPermitted("menu:view");
    }
}
