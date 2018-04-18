package com.github.zhangkaitao.shiro.chapter21.web.shiro.filter;

import com.github.zhangkaitao.shiro.chapter21.Constants;
import com.github.zhangkaitao.shiro.chapter21.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义默认拦截器
 * author : sunpanhu
 * createTime : 2018/4/18 上午9:56
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
