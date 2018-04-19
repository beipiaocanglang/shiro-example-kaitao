package com.github.zhangkaitao.shiro.chapter24.web.shiro.filter;

import com.github.zhangkaitao.shiro.chapter24.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 强制退出拦截器，如果用户会话中存在Constants.SESSION_FORCE_LOGOUT_KEY属性，表示被管理员强制退出了；
 * 然后调用Subject.logout()退出，且重定向到登录页面（自动拼上fourceLogout请求参数）
 * author : sunpanhu
 * createTime : 2018/4/19 下午1:36
 */
public class ForceLogoutFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Session session = getSubject(request, response).getSession(false);
        if(session == null) {
            return true;
        }
        Object attribute = session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY);
        return attribute == null;
    }

    /**
     * 强制退出后会执行
     * author : sunpanhu
     * createTime : 2018/4/19 下午2:00
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            getSubject(request, response).logout();//强制退出
        } catch (Exception e) {
            /*ignore exception*/
            System.out.println("强制退出时出现异常");
        }

        //重定向到登录页面
        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
        WebUtils.issueRedirect(request, response, loginUrl);
        return false;
    }
}
