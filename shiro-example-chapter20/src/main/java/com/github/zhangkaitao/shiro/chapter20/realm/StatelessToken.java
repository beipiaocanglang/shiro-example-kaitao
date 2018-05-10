package com.github.zhangkaitao.shiro.chapter20.realm;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * 用户身份即用户名；
 * 凭证即客户端传入的消息摘要
 * author : sunpanhu
 * createTime : 2018/4/17 下午4:45
 */
public class StatelessToken implements AuthenticationToken {

    private String username;
    private Map<String, ?> params;
    private String clientDigest;

    public StatelessToken(String username,  Map<String, ?> params, String clientDigest) {
        this.username = username;
        this.params = params;
        this.clientDigest = clientDigest;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public  Map<String, ?> getParams() {
        return params;
    }
    public void setParams( Map<String, ?> params) {
        this.params = params;
    }
    public String getClientDigest() {
        return clientDigest;
    }
    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }

    @Override
    public Object getPrincipal() {
       return username;
    }

    @Override
    public Object getCredentials() {
        return clientDigest;
    }
}
