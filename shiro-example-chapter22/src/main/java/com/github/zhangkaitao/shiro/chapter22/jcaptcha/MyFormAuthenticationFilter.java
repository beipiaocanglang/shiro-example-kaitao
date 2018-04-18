package com.github.zhangkaitao.shiro.chapter22.jcaptcha;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 用于验证码验证的Shiro拦截器在用于身份认证的拦截器之前运行；
 * 但是如果验证码验证拦截器失败了，就不需要进行身份认证拦截器流程了；
 * 所以需要修改下如FormAuthenticationFilter身份认证拦截器，当验证码验证失败时不再走身份认证拦截器。
 * 即如果之前已经错了，那直接跳过即可
 * author : sunpanhu
 * createTime : 2018/4/18 上午11:30
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //用户输入的验证码错误时会进去
        if(request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }
}
