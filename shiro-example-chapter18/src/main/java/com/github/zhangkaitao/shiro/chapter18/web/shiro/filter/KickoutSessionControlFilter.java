package com.github.zhangkaitao.shiro.chapter18.web.shiro.filter;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 用于控制并发登录人数的
 * author : sunpanhu
 * createTime : 2018/4/16 下午5:39
 */
public class KickoutSessionControlFilter extends AccessControlFilter {

    private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }
    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }
    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 用于控制并发登录人数的 代码流程 核心代码
     * 此处使用了Cache缓存用户名—会话id之间的关系；
     * 如果量比较大可以考虑如持久化到数据库/其他带持久化的Cache中；
     * 另外此处没有并发控制的同步实现，可以考虑根据用户名获取锁来控制，减少锁的粒度
     * author : sunpanhu
     * createTime : 2018/4/17 上午11:30
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);

        boolean authenticated = subject.isAuthenticated();
        boolean remembered = subject.isRemembered();
        if(!authenticated && !remembered) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();

        //TODO 同步控制
        Deque<Serializable> deque = cache.get(username);
        //如果缓存中没有数据 则同步到缓存中
        if(deque == null) {
            deque = new LinkedList<Serializable>();
            cache.put(username, deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        Object kickout = session.getAttribute("kickout");
        if(!deque.contains(sessionId) && kickout == null) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (kickout != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);
            WebUtils.issueRedirect(request, response, kickoutUrl);
            return false;
        }
        return true;
    }
}
