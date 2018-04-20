package com.github.zhangkaitao.shiro.chapter3;

import junit.framework.Assert;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.junit.Test;

/**
 * 基于资源的访问控制（显示角色）测试代码
 * 也可以叫基于权限的访问控制，这种方式的一般规则是“资源标识符：操作”，即是资源级别的粒度；
 * 这种方式的好处就是如果要修改基本都是一个资源级别的修改，不会对其他模块代码产生影响，粒度小。
 * 但是实现起来可能稍微复杂点，需要维护“用户——角色，角色——权限（资源：操作）”之间的关系。
 * author : sunpanhu
 * createTime : 2018/4/20 下午3:45
 */
public class PermissionTest extends BaseTest {

    /**
     * 判断用户是否拥有某种 或 多种权限 方法一
     * 结果false时 不会抛出异常
     * Shiro提供了isPermitted和isPermittedAll用于判断用户是否拥有某个权限或所有权限，
     * 也没有提供如isPermittedAny用于判断拥有某一个权限的接口。
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:30
     */
    @Test
    public void testIsPermitted() {
        login("classpath:shiro-permission.ini", "zhang", "123");
        //判断拥有权限：user:create
        boolean permitted = subject().isPermitted("user:create");
        Assert.assertTrue(permitted);

        //判断拥有权限：user:update and user:delete
        boolean permittedAll = subject().isPermittedAll("user:update", "user:delete");
        Assert.assertTrue(permittedAll);

        //判断没有权限：user:view
        boolean permitted1 = subject().isPermitted("user:view");
        Assert.assertFalse(permitted1);
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
     * 字符串通配符权限 - 单个资源单个权限 和 单个资源多个权限(及简写)
     * author : sunpanhu
     * createTime : 2018/3/28 上午10:19
     */
    @Test
    public void testWildcardPermission1() {
        login("classpath:shiro-permission.ini", "li", "123");

        // 1、单个资源单个权限 role1、role2、role3
        subject().checkPermissions("system:user:update");

        // 2、单个资源多个权限 role41、role42
        // 单个资源多个权限 用户拥有资源“system:user”的“update”权限
        subject().checkPermissions("system:user:update", "system:user:delete");
        // 用户拥有资源“system:user”的“update”和“delete”权限。如上可以简写成：
        // 通过“system:user:update,delete”验证"system:user:update, system:user:delete"是没问题的，
        // 但是反过来是规则不成立。
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

        // 3、单个资源全部权限 role51、role52、role53
        // 用户拥有资源“system:user”的“create”、“update”、“delete”和“view”所有权限
        subject().checkPermissions("system:user:create,delete,update:view");
        // 如上可以简写成(推荐使用)
        subject().checkPermissions("system:user:*");
        // 也可以简写为
        // 通过“system:user:*”验证“system:user:create,delete,update:view”可以，但是反过来是不成立的
        subject().checkPermissions("system:user");
    }

    /**
     * 字符串通配符权限 - 所有资源全部权限
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:48
     */
    @Test
    public void testWildcardPermission3() {
        login("classpath:shiro-permission.ini", "li", "123");

        // 4、所有资源全部权限
        // 用户拥有所有资源的“view”所有权限。 role61、role62
        subject().checkPermissions("user:view");
        // 假设判断的权限是"system:user:view”，那么需要“role5=*:*:view”这样写才行。
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
        // 5、实例级别
        // 5.1、单个实例单个权限
        //role71=user:view:1
        //对资源user的1实例拥有view权限。
        subject().checkPermissions("user:view:1");

        // 5.2、单个实例多个权限
        //role72="user:update,delete:1"
        //对资源user的1实例拥有update、delete权限
        subject().checkPermissions("user:delete,update:1");
        subject().checkPermissions("user:update:1", "user:delete:1");

        // 5.3、单个实例所有权限
        //role73=user:*:1
        //对资源user的1实例拥有所有权限
        subject().checkPermissions("user:update:1", "user:delete:1", "user:view:1");

        // 5.4、所有实例单个权限
        //role74=user:auth:*
        //对资源user的1实例拥有所有权限。
        subject().checkPermissions("user:auth:1", "user:auth:2");

        // 5.5、所有实例所有权限
        //role75=user:*:*
        //对资源user的1实例拥有所有权限。
        subject().checkPermissions("user:auth:1", "user:auth:2");
    }

    /**
     * Shiro对权限字符串缺失部分的处理
     * 如:
     *     “user:view”等价于“user:view:*”；
     *     而“organization”等价于“organization:*”或者“organization:*:*”。
     *     可以这么理解，这种方式实现了前缀匹配。
     * 另外如:
     *     “user:*”可以匹配如“user:delete”
     *     “user:delete”可以匹配如“user:delete:1”
     *     “user:*:1”可以匹配如“user:view:1”
     *     “user”可以匹配“user:view”或“user:view:1”等。
     *     即*可以匹配所有，不加*可以进行前缀匹配；
     *     但是如“*:view”不能匹配“system:user:view”，需要使用“*:*:view”，即后缀匹配必须指定前缀（多个冒号就需要多个*来匹配）。
     * author : sunpanhu
     * createTime : 2018/3/27 下午5:57
     */
    @Test
    public void testWildcardPermission5() {
        login("classpath:shiro-permission.ini", "li", "123");

        // 6、缺失部分的处理
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
