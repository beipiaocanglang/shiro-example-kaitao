package com.github.zhangkaitao.shiro.chapter10.web.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.io.Serializable;

/**
 * 自定义全局回话监听 实现 SessionListener
 */
public class MySessionListener1 implements SessionListener {

    //会话创建时触发
    public void onStart(Session session) {
        Serializable sessionId = session.getId();
        System.out.println("会话创建：" + sessionId);
    }
    //会话过期时触发
    public void onExpiration(Session session) {
        Serializable sessionId = session.getId();
        System.out.println("会话过期：" + sessionId);
    }
    //退出/会话过期时触发
    public void onStop(Session session) {
        Serializable sessionId = session.getId();
        System.out.println("会话停止：" + sessionId);
    }
}
