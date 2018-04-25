package com.github.zhangkaitao.shiro.chapter6.realm;

import com.github.zhangkaitao.shiro.chapter6.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * 自定义Realm - Realm3
 * author : sunpanhu
 * createTime : 2018/4/25 下午1:14
 */
public class MyRealm3 implements Realm {

    public String getName() {
        //realm name 为 “c”
        return "c";
    }

    public boolean supports(AuthenticationToken token) {
        boolean ins = token instanceof UsernamePasswordToken;
        return ins;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        User user = new User("zhang", "123");
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user, //身份 User类型
                "123",   //凭据
                getName() //Realm Name
        );
        return simpleAuthenticationInfo;
    }
}
