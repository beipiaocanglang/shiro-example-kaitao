package org.apache.shiro.session.mgt;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import javax.servlet.http.HttpServletRequest;
/**
 * 自定义SessionFactory
 * 根据会话上下文创建相应的OnlineSession。
 * 如
 *      用户登录到的系统ip
 *      用户状态（在线 隐身 强制退出）
 *      比如当前所在系统等
 * author : sunpanhu
 * createTime : 2018/4/27 下午3:56
 */
public class OnlineSessionFactory implements SessionFactory {

    public Session createSession(SessionContext initData) {
        OnlineSession session = new OnlineSession();
        if (initData != null && initData instanceof WebSessionContext) {

            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();

            if (request != null) {
                session.setHost(IpUtils.getIpAddr(request));
                session.setUserAgent(request.getHeader("User-Agent"));
                session.setSystemHost(request.getLocalAddr() + ":" + request.getLocalPort());
            }
        }
        return session;
    }
}
