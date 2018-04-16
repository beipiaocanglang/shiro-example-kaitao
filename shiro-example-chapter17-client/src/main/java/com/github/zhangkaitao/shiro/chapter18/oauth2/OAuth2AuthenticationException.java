package com.github.zhangkaitao.shiro.chapter18.oauth2;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义异常
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:40
 */
public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException(Throwable cause) {

        super(cause);
    }
}
