package com.github.zhangkaitao.shiro.chapter8.web.filter;

import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter用于防止多次执行Filter的；
 * 也就是说一次请求只会走一次拦截器链；
 * 另外提供enabled属性，表示是否开启该拦截器实例，默认enabled=true表示开启，
 * 如果不想让某个拦截器工作，可以设置为false即可。
 */
public class MyOncePerRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("=========once per request filter");
        chain.doFilter(request, response);
    }
}
