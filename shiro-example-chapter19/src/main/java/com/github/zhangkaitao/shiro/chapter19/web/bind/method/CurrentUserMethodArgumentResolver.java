package com.github.zhangkaitao.shiro.chapter19.web.bind.method;

import com.github.zhangkaitao.shiro.chapter19.web.bind.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用于绑定@FormModel的方法参数解析器
 * author : sunpanhu
 * createTime : 2018/5/9 下午2:44
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public CurrentUserMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        boolean b = parameter.hasParameterAnnotation(CurrentUser.class);
        if (b) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        Object attribute = webRequest.getAttribute(currentUserAnnotation.value(), NativeWebRequest.SCOPE_REQUEST);

        return attribute;
    }
}
