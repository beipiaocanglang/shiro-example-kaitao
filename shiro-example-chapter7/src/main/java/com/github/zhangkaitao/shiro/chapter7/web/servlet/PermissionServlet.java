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
 * 获取用户权限的请求
 */
@WebServlet(name = "permissionServlet", urlPatterns = "/permission")
public class PermissionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("user:create");

        request.setAttribute("subject", subject);
        request.getRequestDispatcher("/WEB-INF/jsp/hasPermission.jsp").forward(request, response);
    }
}
