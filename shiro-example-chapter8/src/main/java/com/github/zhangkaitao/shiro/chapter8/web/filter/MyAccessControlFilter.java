package com.github.zhangkaitao.shiro.chapter8.web.filter;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 提供了访问控制的基础功能；比如是否允许访问/当访问拒绝时如何处理等：
 */
public class MyAccessControlFilter extends AccessControlFilter {
    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     * author : sunpanhu
     * createTime : 2018/4/2 下午5:19
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println("access allowed");
        return true;
    }
    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可，（比如重定向到另一个页面）
     * author : sunpanhu
     * createTime : 2018/4/2 下午5:19
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("访问拒绝也不自己处理，继续拦截器链的执行");
        return true;
    }
    /**
     * onPreHandle会自动调用这两个方法决定是否继续处理：
     * author : sunpanhu
     * createTime : 2018/4/2 下午5:20
     */
}
