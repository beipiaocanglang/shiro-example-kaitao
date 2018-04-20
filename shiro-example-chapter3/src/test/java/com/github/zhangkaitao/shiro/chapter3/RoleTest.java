package com.github.zhangkaitao.shiro.chapter3;

import junit.framework.Assert;
import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Test;
import java.util.Arrays;

/**
 * 基于角色的访问控制（隐式角色） 测试代码
 * 基于角色的访问控制缺点：
 *      这种方式的缺点就是如果很多地方进行了角色判断，
 *      但是有一天不需要了那么就需要修改相应代码把所有相关的地方进行删除；
 *      这就是粗粒度造成的问题。
 * author : sunpanhu
 * createTime : 2018/4/20 下午3:33
 */
public class RoleTest extends BaseTest {
    /**
     * 编码的实现方式 - 判断用户是否拥有某一个角色 或者 多个角色
     * 结果为false时 不抛异常
     * Shiro提供了hasRole/hasRoles用于判断用户是否拥有某个角色/某些权限；
     * 但是没有提供如hashAnyRole用于判断是否有某些权限中的某一个。
     * author : sunpanhu
     * createTime : 2018/3/27 下午4:52
     */
    @Test
    public void testHasRole() {
        login("classpath:shiro-role.ini", "zhang", "123");

        //判断拥有角色：role1
        boolean role1 = subject().hasRole("role1");
        Assert.assertTrue(role1);

        //判断拥有角色：role1 and role2
        boolean b = subject().hasAllRoles(Arrays.asList("role1", "role2"));
        Assert.assertTrue(b);

        //判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        System.out.println("result[0] -- " + result[0]);
        System.out.println("result[1] -- " + result[1]);
        System.out.println("result[2] -- " + result[2]);

        Assert.assertEquals(true, result[0]);
        Assert.assertEquals(true, result[1]);
        Assert.assertEquals(false, result[2]);
    }

    /**
     * 判断用户是否拥有某个角色 方式二
     * 结果为false时 会抛出异常 UnauthorizedException
     * Shiro提供的checkRole/checkRoles和hasRole/hasAllRoles不同的地方是它在判断为假的情况下会抛出UnauthorizedException异常
     * author : sunpanhu
     * createTime : 2018/3/27 下午4:59
     */
    @Test(expected = UnauthorizedException.class)
    public void testCheckRole() {
        login("classpath:shiro-role.ini", "zhang", "123");

        //断言拥有角色：role1
        subject().checkRole("role1");

        //断言拥有角色：role1 and role3 失败抛出异常
        subject().checkRoles("role1", "role3");
    }
}
