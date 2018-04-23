package com.github.zhangkaitao.shiro.chapter4;

import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 初始化 ini 配置文件的main
 * author : sunpanhu
 * createTime : 2018/4/23 下午1:48
 */
public class IniMainTest {

    @Test
    public void test() {
        //创建Factory
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-config-main.ini");
        //创建SecurityManager对象 创建时会调用ModularRealmAuthenticator 参照 com.github.zhangkaitao.shiro.chapter4.authenticator.MyAuthenticator
        SecurityManager securityManager = factory.getInstance();
        //将SecurityManager设置到SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);
        //获取Subject对象
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);

        Assert.assertTrue(subject.isAuthenticated());
    }
}
