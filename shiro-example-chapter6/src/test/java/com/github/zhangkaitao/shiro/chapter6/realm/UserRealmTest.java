package com.github.zhangkaitao.shiro.chapter6.realm;

import com.github.zhangkaitao.shiro.chapter6.BaseTest;
import junit.framework.Assert;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.Test;

/**
 * 用户Realm测试
 */
public class UserRealmTest extends BaseTest {
    /**
     * 测试用户登录
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:59
     */
    @Test
    public void testLoginSuccess() {
        login("classpath:shiro.ini", u1.getUsername(), password);
        Assert.assertTrue(subject().isAuthenticated());
    }

    /**
     * 测试用户名不存在
     * author : sunpanhu
     * createTime : 2018/4/2 下午1:59
     */
    @Test(expected = UnknownAccountException.class)
    public void testLoginFailWithUnknownUsername() {
        login("classpath:shiro.ini", u1.getUsername() + "1", password);
    }

    /**
     * 测试密码错误
     * author : sunpanhu
     * createTime : 2018/4/2 下午2:00
     */
    @Test(expected = IncorrectCredentialsException.class)
    public void testLoginFailWithErrorPassowrd() {
        login("classpath:shiro.ini", u1.getUsername(), password + "1");
    }

    /**
     * 测试账号已锁定
     * author : sunpanhu
     * createTime : 2018/4/2 下午2:00
     */
    @Test(expected = LockedAccountException.class)
    public void testLoginFailWithLocked() {
        login("classpath:shiro.ini", u4.getUsername(), password + "1");
    }

    /**
     * 测试账号连续5次输入错误
     * author : sunpanhu
     * createTime : 2018/4/2 下午2:00
     */
    @Test(expected = ExcessiveAttemptsException.class)
    public void testLoginFailWithLimitRetryCount() {
        for(int i = 1; i <= 5; i++) {
            try {
                login("classpath:shiro.ini", u3.getUsername(), password + "1");
            } catch (Exception e) {/*ignore*/}
        }
        login("classpath:shiro.ini", u3.getUsername(), password + "1");

        //需要清空缓存，否则后续的执行就会遇到问题(或者使用一个全新账户测试)
    }

    /**
     * 测试用户拥有admin角色
     * author : sunpanhu
     * createTime : 2018/4/2 下午2:02
     */
    @Test
    public void testHasRole() {
        login("classpath:shiro.ini", u1.getUsername(), password );
        Assert.assertTrue(subject().hasRole("admin"));
    }

    /**
     * 测试用户没有对应的角色
     * author : sunpanhu
     * createTime : 2018/4/2 下午2:01
     */
    @Test
    public void testNoRole() {
        login("classpath:shiro.ini", u2.getUsername(), password);
        Assert.assertFalse(subject().hasRole("admin"));
    }

    /**
     * 测试用户拥有"user:create", "menu:create"权限
     * author : sunpanhu
     * createTime : 2018/4/2 下午2:02
     */
    @Test
    public void testHasPermission() {
        login("classpath:shiro.ini", u1.getUsername(), password);
        Assert.assertTrue(subject().isPermittedAll("user:create", "menu:create"));
    }

    /**
     * 测试用户灭幼"user:create"权限
     * author : sunpanhu
     * createTime : 2018/4/2 下午2:02
     */
    @Test
    public void testNoPermission() {
        login("classpath:shiro.ini", u2.getUsername(), password);
        Assert.assertFalse(subject().isPermitted("user:create"));
    }
}
