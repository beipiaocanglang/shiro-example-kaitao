package com.github.zhangkaitao.shiro.chapter18.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 类似于UsernamePasswordToken和CasToken；用于存储oauth2服务端返回的auth code。
 * 在自定义Realm中会用到这个
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:30
 */
public class OAuth2Token implements AuthenticationToken {

    private String authCode;
    private String principal;

    public OAuth2Token(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    public Object getCredentials() {
        return authCode;
    }
}
