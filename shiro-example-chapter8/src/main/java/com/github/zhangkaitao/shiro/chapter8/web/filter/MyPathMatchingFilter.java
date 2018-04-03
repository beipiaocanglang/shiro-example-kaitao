package com.github.zhangkaitao.shiro.chapter8.web.filter;

import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Arrays;

/**
 * PathMatchingFilter 继承了 AdviceFilter
 * 提供了url模式过滤的功能，如果需要对指定的请求进行处理
 * 提供了基于Ant风格的请求路径匹配功能及拦截器参数解析的功能，
 * 如：
 *      “roles[admin,user]”自动根据“，”分割解析到一个路径参数配置并绑定到相应的路径
 */
public class MyPathMatchingFilter extends PathMatchingFilter {

    /**
     * 在preHandle中，当pathsMatch匹配一个路径后，会调用opPreHandler方法并将路径绑定参数配置传给mappedValue；
     * 然后可以在这个方法中进行一些验证（如角色授权），如果验证失败可以返回false中断流程；
     * 默认返回true；
     * 也就是说子类可以只实现onPreHandle即可，无须实现preHandle。
     * 如果没有path与请求路径匹配，默认是通过的（即preHandle返回true）
     * author : sunpanhu
     * createTime : 2018/4/2 下午5:18
     */
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println("url matches,config is " + Arrays.toString((String[])mappedValue));
        return true;
    }
}