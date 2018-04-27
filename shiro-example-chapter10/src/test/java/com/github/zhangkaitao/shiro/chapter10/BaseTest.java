package com.github.zhangkaitao.shiro.chapter10;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;

/**
 * session会话测试通用基本类
 * author : sunpanhu
 * createTime : 2018/4/27 下午1:45
 */
public abstract class BaseTest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        //退出时请解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
    }

    /**
     * 通用的登录方法
     * author : sunpanhu
     * createTime : 2018/4/27 下午1:22
     */
    protected void login(String configFile, String username, String password) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setHost("10.83.1.1");

        try {
            subject.login(token);
            System.out.println("登录成功——————————————————————————————");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("认证失败—————————————————————————————");
        }
    }

    //获取subject对象
    public Subject subject() {
        return SecurityUtils.getSubject();
    }
}
