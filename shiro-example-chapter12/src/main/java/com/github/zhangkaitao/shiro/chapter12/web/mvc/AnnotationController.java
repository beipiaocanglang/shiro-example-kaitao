package com.github.zhangkaitao.shiro.chapter12.web.mvc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试 权限注解的 controller
 * author : sunpanhu
 * createTime : 2018/5/2 下午2:50
 */
@Controller
public class AnnotationController {

    @RequestMapping("/hello1")
    public String hello1() {
        SecurityUtils.getSubject().checkRole("admin");
        return "success";
    }

    /**
     * 必须拥有 admin 角色的用户才能访问
     * 当验证失败，其会抛出UnauthorizedException异常，
     * 此时可以使用Spring的ExceptionHandler（DefaultExceptionHandler）来进行拦截处理：
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping("/hello2")
    public String hello2() {
        return "success";
    }
}
