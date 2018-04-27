package com.github.zhangkaitao.shiro.chapter10;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;

/**
 * session会话测试
 * author : sunpanhu
 * createTime : 2018/4/27 下午1:45
 */
public class SessionTest extends BaseTest {

    @Test
    public void testGetSession() throws Exception{
        login("classpath:shiro.ini", "zhang", "123");
        Subject subject = subject();
        Session session = subject.getSession();//获取会话

        // 获取当前会话的唯一标识,会话ID
        Serializable sessionId = session.getId();
        // 获取当前登录用户主机地址,该地址是通过HostAuthenticationToken.getHost()提供的。
        String host = session.getHost();
        // 获取/设置当前Session的过期时间；如果不设置默认是会话管理器的全局过期时间。
        // session.setTimeout(毫秒);
        long timeout = session.getTimeout();
        // 获取会话的启动时间及最后访问时间；
        // 如果是JavaSE应用需要自己定期调用session.touch()去更新最后访问时间；
        // 如果是Web应用，每次进入ShiroFilter都会自动调用session.touch()来更新最后访问时间。
        Date startTimestamp = session.getStartTimestamp();
        Date lastAccessTime = session.getLastAccessTime();

        // 睡眠1秒钟
        Thread.sleep(1000L);
        // 更新会话最后访问时间
        session.touch();
        // 获取最后访问时间
        Date lastAccessTimeTwo = session.getLastAccessTime();
        // 销毁会话
        // 当Subject.logout()时会自动调用stop方法来销毁会话。
        // 如果在web中，调用javax.servlet.http.HttpSession. invalidate()也会自动调用Shiro Session.stop方法进行销毁Shiro的会话
        //session.stop();

        // 设置/获取/删除会话属性；在整个会话范围内都可以对这些属性进行操作。
        session.setAttribute("key", "123");
        Object key = session.getAttribute("key");
        session.removeAttribute("key");
        Object sessionKey = session.getAttribute("key");
    }
}
