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
 * 基于Basic的拦截器身份验证
 * 通过ini配置成功、失败、没有权限、没有角色的跳转
 */
@WebServlet(name = "roleServlet", urlPatterns = "/role")
public class RoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        request.setAttribute("subject", subject);
        request.getRequestDispatcher("/WEB-INF/jsp/hasRole.jsp").forward(request, response);
    }
}
