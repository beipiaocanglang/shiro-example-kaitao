package com.github.zhangkaitao.shiro.chapter7.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有角色 或 权限不足时重定向的请求
 */
@WebServlet(name = "unauthorizedServlet", urlPatterns = "/unauthorized")
public class UnAuthorizedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/unauthorized.jsp").forward(request, response);
    }
}
