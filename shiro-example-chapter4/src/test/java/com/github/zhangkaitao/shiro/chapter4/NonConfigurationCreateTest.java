package com.github.zhangkaitao.shiro.chapter4;

import com.alibaba.druid.pool.DruidDataSource;
import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.Arrays;

/**
 * 从之前的Shiro架构图可以看出，Shiro是从根对象SecurityManager进行身份验证和授权的；
 * 也就是所有操作都是自它开始的，这个对象是线程安全且真个应用只需要一个即可，
 * 因此Shiro提供了SecurityUtils让我们绑定它为全局的，方便后续操作。
 * 因为Shiro的类都是POJO的，因此都很容易放到任何IoC容器管理。
 * 但是和一般的IoC容器的区别在于，Shiro从根对象securityManager开始导航；
 * Shiro支持的依赖注入：public空参构造器对象的创建、setter依赖注入
 *
 * SecurityManager是线程安全且真个应用只需要一个即可
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-27
 * <p>Version: 1.0
 */
public class NonConfigurationCreateTest {

    /**
     * 纯java代码实现 全局SecurityManager设置
     *
     * 等价与 ConfigurationCreateTest.java 和 对应的 shiro-config.ini配置
     *
     * author : sunpanhu
     * createTime : 2018/3/28 上午11:05
     */
    @Test
    public void test() {

        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //设置authenticator
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);

        //设置authorizer
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);

        //设置Realm
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/shiro");
        ds.setUsername("root");
        ds.setPassword("root");

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(ds);
        jdbcRealm.setPermissionsLookupEnabled(true);
        securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));

        //将SecurityManager设置到SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);

        Assert.assertTrue(subject.isAuthenticated());
        boolean authenticated = subject.isAuthenticated();
    }
}
