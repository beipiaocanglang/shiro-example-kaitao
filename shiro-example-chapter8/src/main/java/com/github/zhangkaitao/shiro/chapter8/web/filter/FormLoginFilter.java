package com.github.zhangkaitao.shiro.chapter8.web.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * shiro提供的基于表单提交的拦截
 */
public class FormLoginFilter extends PathMatchingFilter {

    private String loginUrl = "/login.jsp";
    private String successUrl = "/";

    /**
     * onPreHandle主要流程：
     *      1、首先判断是否已经登录过了，如果已经登录过了继续拦截器链即可；
     *      2、如果没有登录，看看是否是登录请求，如果是get方法的登录页面请求，则继续拦截器链（到请求页面），否则如果是get方法的其他页面请求则保存当前请求并重定向到登录页面；
     *      3、如果是post方法的登录页面表单提交请求，则收集用户名/密码登录即可，如果失败了保存错误消息到“shiroLoginFailure”并返回到登录页面；
     *      4、如果登录成功了，且之前有保存的请求，则重定向到之前的这个请求，否则到默认的成功页面。
     * 启动服务器输入http://localhost:8080/chapter8/test.jsp测试时，会自动跳转到登录页面，登录成功后又会跳回到test.jsp页面。
     * author : sunpanhu
     * createTime : 2018/4/3 下午3:54
     */
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //验证用户是否登录
        boolean authenticated = SecurityUtils.getSubject().isAuthenticated();
        if(authenticated) {
            return true;//已经登录过
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //调用下面的isLoginRequest方法
        boolean loginRequest = isLoginRequest(req);

        if(loginRequest) {
            String method = req.getMethod();
            if("post".equalsIgnoreCase(method)) {//form表单提交
                //登录  调用下面的login方法
                boolean loginSuccess = login(req);

                if(loginSuccess) {
                    //调用下面的重定向到成功页面的url方法
                    boolean redirectToSuccessUrl = redirectToSuccessUrl(req, resp);
                    return redirectToSuccessUrl;
                }
            }
            return true;//继续过滤器链
        } else {
            //保存当前地址并重定向到登录界面  调用下面的方法
            saveRequestAndRedirectToLogin(req, resp);
            return false;
        }
    }

    //重定向到成功页面
    private boolean redirectToSuccessUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebUtils.redirectToSavedRequest(req, resp, successUrl);
        return false;
    }

    //保存重定向请求到登陆页面
    private void saveRequestAndRedirectToLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebUtils.saveRequest(req);
        WebUtils.issueRedirect(req, resp, loginUrl);
    }

    //登陆
    private boolean login(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        } catch (Exception e) {
            req.setAttribute("shiroLoginFailure", e.getClass());
            return false;
        }
        return true;
    }

    //是否是登陆请求
    private boolean isLoginRequest(HttpServletRequest req) {
        boolean b = pathsMatch(loginUrl, WebUtils.getPathWithinApplication(req));
        return b;
    }
}
