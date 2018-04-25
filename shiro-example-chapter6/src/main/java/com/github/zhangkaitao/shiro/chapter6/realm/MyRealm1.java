package com.github.zhangkaitao.shiro.chapter6.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
/**
 * 自定义Realm - Realm1
 * author : sunpanhu
 * createTime : 2018/4/25 下午1:14
 */
public class MyRealm1 implements Realm {

    public String getName() {
        //realm name 为 “a”
        return "a";
    }

    public boolean supports(AuthenticationToken token) {
        boolean ins = token instanceof UsernamePasswordToken;
        return ins;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                "zhang", //身份 字符串类型
                "123",   //凭据
                getName() //Realm Name
        );
        return simpleAuthenticationInfo;
    }
}
