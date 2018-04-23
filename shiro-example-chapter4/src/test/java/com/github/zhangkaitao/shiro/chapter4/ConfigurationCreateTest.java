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
 * 通过 shiro-config.ini 配置文件 设置全局的 SecurityManager
 * author : sunpanhu
 * createTime : 2018/4/23 下午1:36
 */
public class ConfigurationCreateTest {

    /**
     * 如上代码是从Shiro INI配置中获取相应的securityManager实例：
     *      1、默认情况先创建一个名字为securityManager，类型为org.apache.shiro.mgt.DefaultSecurityManager的默认的SecurityManager，
     *          如果想自定义，只需要在ini配置文件中指定“securityManager=SecurityManager实现类”即可，
     *          名字必须为securityManager，它是起始的根；
     *      2、IniSecurityManagerFactory是创建securityManager的工厂，其需要一个ini配置文件路径，
     *          其支持“classpath:”（类路径）、“file:”（文件系统）、“url:”（网络）三种路径格式，
     *          默认是文件系统；
     *      3、接着获取SecuriyManager实例，后续步骤和之前的一样。
     *
     *      如下代码可以看出Shiro INI配置方式本身提供了一个简单的IoC/DI机制方便在配置文件配置，
     *      但是是从securityManager这个根对象开始导航。
     * author : sunpanhu
     * createTime : 2018/4/23 下午1:39
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
