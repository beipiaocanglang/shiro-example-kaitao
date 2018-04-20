package com.github.zhangkaitao.shiro.chapter2;

import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

/**
 * 自定义认证策略
 * author : sunpanhu
 * createTime : 2018/4/20 上午11:32
 */
public class AuthenticatorTest {
    /**
     * 所有Realm验证成功才算成功，且返回所有Realm身份验证成功的认证信息，如果有一个失败就失败了。
     * login(String configFile) 此方法中设置了固定的用户名和密码 zhangsan 123
     * org.apache.shiro.authc.pam.AllSuccessfulStrategy
     * MyRealm1 和 MyRealm3配合会返回 zhang,zhang@163.com
     * MyRealm2 和 任何一个自定义realm配合 结果为null
     *
     * author : sunpanhu
     * createTime : 2018/3/27 下午3:52
     */
    @Test
    public void testAllSuccessfulStrategyWithSuccess() {
        login("classpath:shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，其包含了Realm验证成功的身份信息  即PrincipalCollection包含了zhang和zhang@163.com身份信息。
        PrincipalCollection principalCollection = subject.getPrincipals();

        //junit的一些断言方法如下
        Assert.assertEquals(2, principalCollection.asList().size());
    }

    /**
     * 这个test方法是 上面 testAllSuccessfulStrategyWithSuccess 方法的反向案例
     * 是MyRealm1 和 MyRealm2 配合使用的 结果是 null
     * author : sunpanhu
     * createTime : 2018/3/27 下午4:08
     */
    @Test(expected = UnknownAccountException.class)
    public void testAllSuccessfulStrategyWithFail() {

        login("classpath:shiro-authenticator-all-fail.ini");

        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        //junit的一些断言方法如下
        Assert.assertEquals(2, principalCollection.asList().size());
    }

    /**
     * 多个自定义realm中返回所有验证成功的用户身份的集合
     * 例如：
     *      MyRealm1、 MyRealm2、 MyRealm3 其中realm2是验证失败的  则会返回realm1和realm3的用户信息集合
     * org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy
     *
     * author : sunpanhu
     * createTime : 2018/3/27 下午4:12
     */
    @Test
    public void testAtLeastOneSuccessfulStrategyWithSuccess() {
        login("classpath:shiro-authenticator-atLeastOne-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(2, principalCollection.asList().size());
    }

    /**
     * 多个自定义realm中返回 第一个验证成功的用户身份信息
     * 例如：
     *      MyRealm1、 MyRealm2、 MyRealm3 其中realm2是验证失败的  则会返回realm1的用户信息集合
     * org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy
     *
     * author : sunpanhu
     * createTime : 2018/3/27 下午4:12
     */
    @Test
    public void testFirstOneSuccessfulStrategyWithSuccess() {
        login("classpath:shiro-authenticator-first-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，其包含了第一个Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
    }

    /**
     * 只返回验证成功的用户身份信息 如果有多个相同的身份信息同时验证通过时  只会返回一个
     * com.github.zhangkaitao.shiro.chapter2.authenticator.strategy.AtLeastTwoAuthenticatorStrategy 自定义实现类 继承了 AbstractAuthenticationStrategy
     * author : sunpanhu
     * createTime : 2018/3/27 下午4:24
     */
    @Test
    public void testAtLeastTwoStrategyWithSuccess() {
        login("classpath:shiro-authenticator-atLeastTwo-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
    }

    /**
     * 只有一个成功时会返回用户身份信息
     * 如果全部失败 或者 有多个成功 则会返回null
     * com.github.zhangkaitao.shiro.chapter2.authenticator.strategy.OnlyOneAuthenticatorStrategy 自定义类 继承了AbstractAuthenticationStrategy
     * author : sunpanhu
     * createTime : 2018/3/27 下午4:21
     */
    @Test
    public void testOnlyOneStrategyWithSuccess() {
        login("classpath:shiro-authenticator-onlyone-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
    }

    /**
     * 通用化登录逻辑
     * author : sunpanhu
     * createTime : 2018/3/27 下午4:28
     */
    private void login(String configFile) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
}
