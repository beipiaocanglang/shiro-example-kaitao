package com.github.zhangkaitao.shiro.chapter10.web.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

import java.io.Serializable;

/**
 * 自定义单独某一个回话监听 继承 SessionListenerAdapter
 */
public class MySessionListener2 extends SessionListenerAdapter {
    @Override
    public void onStart(Session session) {
        Serializable sessionId = session.getId();
        System.out.println("会话创建：" + sessionId);
    }
}
