package com.github.zhangkaitao.shiro.chapter2;

import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

/**
 * 登录/退出/自定义Realm用例
 * author : sunpanhu
 * createTime : 2018/4/19 下午5:34
 */
public class LoginLogoutTest {

    /**
     * shiro 入门案例 登录、登出
     * 使用的时默认的realm
     * 代码执行流程：
     *      1、首先通过new IniSecurityManagerFactory并指定一个ini配置文件来创建一个SecurityManager工厂；
     *      2、接着获取SecurityManager并绑定到SecurityUtils，这是一个全局设置，设置一次即可；
     *      3、通过SecurityUtils得到Subject，其会自动绑定到当前线程；如果在web环境在请求结束时需要解除绑定；
     *         然后获取身份验证的Token，如用户名/密码；
     *      4、调用subject.login方法进行登录，其会自动委托给SecurityManager.login方法进行登录；
     *      5、如果身份验证失败请捕获AuthenticationException或其子类，
     *         常见的如：
     *              DisabledAccountException（禁用的帐号）、LockedAccountException（锁定的帐号）、
     *              UnknownAccountException（错误的帐号）、ExcessiveAttemptsException（登录失败次数过多）、
     *              IncorrectCredentialsException （错误的凭证）、ExpiredCredentialsException（过期的凭证）等，
     *              具体请查看其继承关系；
     *              对于页面的错误消息展示，最好使用如“用户名/密码错误”而不是“用户名错误”/“密码错误”，防止一些恶意用户非法扫描帐号库；
     *      6、最后可以调用subject.logout退出，其会自动委托给SecurityManager.logout方法退出。
     * author : sunpanhu
     * createTime : 2018/3/27 下午2:32
     */
    @Test
    public void testHelloworld() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }

        boolean authenticated = subject.isAuthenticated();//用户是否认证
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }

    /**
     * 自定义realm案例
     * author : sunpanhu
     * createTime : 2018/3/27 下午2:33
     */
    @Test
    public void testCustomRealm() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            //4、登录，即身份验证 登录时会走自定义realm来校验
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            e.printStackTrace();
        }

        boolean authenticated = subject.isAuthenticated();//用户是否认证
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }

    /**
     * 自定义多个realm
     * 多个realm执行顺序：顺序执行
     * 多个realm只要有一个realm执行通过就算成功
     * author : sunpanhu
     * createTime : 2018/3/27 下午2:41
     */
    @Test
    public void testCustomMultiRealm() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-multi-realm.ini");

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            //4、登录，即身份验证  登录时会走自定义realm来校验
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            e.printStackTrace();
        }
        boolean authenticated = subject.isAuthenticated();//用户是否认证
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }

    /**
     * 使用从数据库中查到的用户名和密码来校验用户
     * author : sunpanhu
     * createTime : 2018/3/27 下午2:47
     */
    @Test
    public void testJDBCRealm() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang1", "123");

        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            e.printStackTrace();
        }
        boolean authenticated = subject.isAuthenticated();
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }

    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
}
