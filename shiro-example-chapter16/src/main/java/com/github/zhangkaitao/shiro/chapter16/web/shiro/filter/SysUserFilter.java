package com.github.zhangkaitao.shiro.chapter16.web.shiro.filter;

import com.github.zhangkaitao.shiro.chapter16.Constants;
import com.github.zhangkaitao.shiro.chapter16.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 用于根据当前登录用户身份获取User信息放入request；然后就可以通过request获取User
 * author : sunpanhu
 * createTime : 2018/4/9 上午10:11
 */
public class SysUserFilter extends PathMatchingFilter {

    @Autowired
    private UserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String)SecurityUtils.getSubject().getPrincipal();
        request.setAttribute(Constants.CURRENT_USER, userService.findByUsername(username));
        return true;
    }
}
