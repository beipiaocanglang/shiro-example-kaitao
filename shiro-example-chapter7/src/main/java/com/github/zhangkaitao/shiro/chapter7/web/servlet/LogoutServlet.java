package com.github.zhangkaitao.shiro.chapter7.web.servlet;

import org.apache.shiro.SecurityUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 方式一：退出请求
 * 直接在ini配置文件部分定义 /login=anon，
 * 登录成功后访问/logout请求 调用本servlet即可退出。
 *
 * 方式二：直接在ini配置文件中配置shiro内置退出
 * [main]
 * logout.redirectUrl=/login
 * [urls]
 * /logout2=logout
 */
@WebServlet(name = "logoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SecurityUtils.getSubject().logout();
        req.getRequestDispatcher("/WEB-INF/jsp/logoutSuccess.jsp").forward(req, resp);
    }
}
