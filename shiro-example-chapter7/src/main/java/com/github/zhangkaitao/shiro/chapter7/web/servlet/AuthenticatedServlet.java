package com.github.zhangkaitao.shiro.chapter7.web.servlet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取当前用户的认证情况
 */
@WebServlet(name = "authenticatedServlet", urlPatterns = "/authenticated")
public class AuthenticatedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("/WEB-INF/jsp/authenticated.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }
}
