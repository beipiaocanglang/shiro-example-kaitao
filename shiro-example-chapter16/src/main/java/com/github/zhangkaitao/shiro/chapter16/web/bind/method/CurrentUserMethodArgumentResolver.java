package com.github.zhangkaitao.shiro.chapter16.web.bind.method;

import com.github.zhangkaitao.shiro.chapter16.web.bind.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用于绑定@FormModel的方法参数解析器
 * 此处注册了一个@CurrentUser参数解析器。
 * 如之前的IndexController，从request获取shiro sysUser拦截器放入的当前登录User对象。
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public CurrentUserMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        //这里的作用其实就是根据key去request中获取数据 (request中的用户信息是 自定义Realm 认证是存入session中的)
        Object attribute = webRequest.getAttribute(currentUserAnnotation.value(), NativeWebRequest.SCOPE_REQUEST);
        return attribute;
    }
}
