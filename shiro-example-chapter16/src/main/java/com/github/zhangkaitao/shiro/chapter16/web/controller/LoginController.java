package com.github.zhangkaitao.shiro.chapter16.web.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录操作
 * 用于显示登录表单页面，其中shiro authc拦截器进行登录，
 * 登录失败的话会把错误存到shiroLoginFailure属性中，
 * 在该控制器中获取后来显示相应的错误信息
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login"    )
    public String showLoginForm(HttpServletRequest req, Model model){
        //指定登录失败时的request属性key（默认shiroLoginFailure）；这样可以在登录表单得到该错误key显示相应的错误消息；
        //authc.failureKeyAttribute=shiroLoginFailure
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }
}
