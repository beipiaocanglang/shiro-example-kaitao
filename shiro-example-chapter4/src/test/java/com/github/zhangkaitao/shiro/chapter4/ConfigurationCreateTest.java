package com.github.zhangkaitao.shiro.chapter4;

import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 设置全局的SecurityManager
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-27
 * <p>Version: 1.0
 */
public class ConfigurationCreateTest {

    /**
     * 通过 shiro-config.ini 配置文件 设置全局的 SecurityManager
     * author : sunpanhu
     * createTime : 2018/3/28 上午11:12
     */
    @Test
    public void test() {

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-config.ini");

        SecurityManager securityManager = factory.getInstance();

        //将SecurityManager设置到SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);

        Assert.assertTrue(subject.isAuthenticated());
    }
}
