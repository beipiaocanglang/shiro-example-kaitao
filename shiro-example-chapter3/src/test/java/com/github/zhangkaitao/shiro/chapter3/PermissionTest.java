package com.github.zhangkaitao.shiro.chapter3;

import junit.framework.Assert;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.junit.Test;

/**
 * 权限判断测试代码
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-26
 * <p>Version: 1.0
 */
public class PermissionTest extends BaseTest {

    /**
     * 判断用户是否拥有某种 或 多种权限 方法一
     * 结果false时 不会抛出异常
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:30
     */
    @Test
    public void testIsPermitted() {
        login("classpath:shiro-permission.ini", "zhang", "123");
        //判断拥有权限：user:create
        Assert.assertTrue(subject().isPermitted("user:create"));
        //判断拥有权限：user:update and user:delete
        Assert.assertTrue(subject().isPermittedAll("user:update", "user:delete"));
        //判断没有权限：user:view
        Assert.assertFalse(subject().isPermitted("user:view"));
    }

    /**
     * 判断用户是否拥有某种 或 多种权限 方法二
     * 结果false时 会抛出异常
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:30
     */
    @Test(expected = UnauthorizedException.class)
    public void testCheckPermission() {
        login("classpath:shiro-permission.ini", "zhang", "123");
        //断言拥有权限：user:create
        subject().checkPermission("user:create");
        //断言拥有权限：user:delete and user:update
        subject().checkPermissions("user:delete", "user:update");
        //断言拥有权限：user:view 失败抛出异常
        subject().checkPermissions("user:view");
    }

    /**
     * 字符串通配符权限 - 性能问题
     *      通配符匹配方式比字符串相等匹配来说是更复杂的，因此需要花费更长时间，
     *      但是一般系统的权限不会太多，且可以配合缓存来提供其性能，
     *      如果这样性能还达不到要求我们可以实现位操作算法实现性能更好的权限匹配。
     *      另外实例级别的权限验证如果数据量太大也不建议使用，可能造成查询权限及匹配变慢。
     *      可以考虑比如在sql查询时加上权限字符串之类的方式在查询时就完成了权限匹配。
     * author : sunpanhu
     * createTime : 2018/3/28 上午10:19
     */
    /**
     * 字符串通配符权限 - 单个资源 单个权限 和 多个权限(及简写)
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:39
     */
    @Test
    public void testWildcardPermission1() {
        login("classpath:shiro-permission.ini", "li", "123");

        //单个资源单个权限
        subject().checkPermissions("system:user:update");

        //单个资源多个权限 用户拥有资源“system:user”的“update”权限
        subject().checkPermissions("system:user:update", "system:user:delete");

        //用户拥有资源“system:user”的“update”和“delete”权限。如上可以简写成：
        subject().checkPermissions("system:user:update,delete");
    }

    /**
     * 字符串通配符权限 - 单个资源全部权限(及简写)
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:43
     */
    @Test
    public void testWildcardPermission2() {
        login("classpath:shiro-permission.ini", "li", "123");

        //用户拥有资源“system:user”的“create”、“update”、“delete”和“view”所有权限
        subject().checkPermissions("system:user:create,delete,update:view");
        //如上可以简写成(推荐使用)
        subject().checkPermissions("system:user:*");
        //也可以简写为
        subject().checkPermissions("system:user");
    }

    /**
     * 字符串通配符权限 - 所有资源全部权限
     * 针对角色role61 和 role62
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:48
     */
    @Test
    public void testWildcardPermission3() {
        login("classpath:shiro-permission.ini", "li", "123");

        subject().checkPermissions("user:view");

        subject().checkPermissions("system:user:view");
    }

    /**
     * 字符串通配符权限 - 实例级别
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:49
     */
    @Test
    public void testWildcardPermission4() {
        login("classpath:shiro-permission.ini", "li", "123");
        //单个实例单个权限
        //role71=user:view:1
        //对资源user的1实例拥有view权限。
        subject().checkPermissions("user:view:1");

        //单个实例多个权限
        //role72="user:update,delete:1"
        //对资源user的1实例拥有update、delete权限
        subject().checkPermissions("user:delete,update:1");
        subject().checkPermissions("user:update:1", "user:delete:1");

        //单个实例所有权限
        //role73=user:*:1
        //对资源user的1实例拥有所有权限
        subject().checkPermissions("user:update:1", "user:delete:1", "user:view:1");

        //所有实例单个权限
        //role74=user:auth:*
        //对资源user的1实例拥有所有权限。
        subject().checkPermissions("user:auth:1", "user:auth:2");

        //所有实例所有权限
        //role75=user:*:*
        //对资源user的1实例拥有所有权限。
        subject().checkPermissions("user:auth:1", "user:auth:2");

    }

    /**
     * Shiro对权限字符串缺失部分的处理
     *      如:
     *          “user:view”等价于“user:view:*”；
     *          而“organization”等价于“organization:*”或者“organization:*:*”。
     *          可以这么理解，这种方式实现了前缀匹配。
     *      另外如:
     *          “user:*”可以匹配如“user:delete”
     *          “user:delete”可以匹配如“user:delete:1”
     *          “user:*:1”可以匹配如“user:view:1”
     *          “user”可以匹配“user:view”或“user:view:1”等。
     *          即*可以匹配所有，不加*可以进行前缀匹配；
     *          但是如“*:view”不能匹配“system:user:view”，需要使用“*:*:view”，即后缀匹配必须指定前缀（多个冒号就需要多个*来匹配）。
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:57
     */
    @Test
    public void testWildcardPermission5() {
        login("classpath:shiro-permission.ini", "li", "123");
        subject().checkPermissions("menu:view:1");

        subject().checkPermissions("organization");
        subject().checkPermissions("organization:view");
        subject().checkPermissions("organization:view:1");
    }

    /**
     * 等价匹配 没有特殊要求 直接使用字符串比较方便
     * role81 和 role82
     * author : sunpanhu
     * createTime : 2018/3/27 下午6:00
     */
    @Test
    public void testWildcardPermission6() {
        login("classpath:shiro-permission.ini", "li", "123");
        subject().checkPermission("menu:view:1");
        subject().checkPermission(new WildcardPermission("menu:view:1"));
    }
}
