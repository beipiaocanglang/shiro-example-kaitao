package com.github.zhangkaitao.shiro.chapter7.web.servlet;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基于表单的拦截器身份验证
 * http://localhost:8080/chapter7/formfilterlogin
 * 如果访问此请求  如果在这之前操作过需要清缓存
 */
@WebServlet(name = "formFilterLoginServlet", urlPatterns = "/formfilterlogin")
public class FormFilterLoginServlet extends HttpServlet {

    /**
     * 访问http://localhost:8080/chapter7/formfilterlogin时走doGet
     * author : sunpanhu
     * createTime : 2018/4/2 下午4:31
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * 基于form表单的用户登录
     * author : sunpanhu
     * createTime : 2018/4/2 下午4:31
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取request中的错误信息 在这一步之前会根据用户输入的用户名和密码在ini配置中校验用户名和密码是否正确
        String errorClassName = (String)request.getAttribute("shiroLoginFailure");

        if(UnknownAccountException.class.getName().equals(errorClassName)) {
            request.setAttribute("error", "用户名/密码错误");
        } else if(IncorrectCredentialsException.class.getName().equals(errorClassName)) {
            request.setAttribute("error", "用户名/密码错误");
        } else if(errorClassName != null) {
            request.setAttribute("error", "未知错误：" + errorClassName);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/formfilterlogin.jsp").forward(request, response);
    }
}
