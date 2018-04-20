package com.github.zhangkaitao.shiro.chapter2.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * 自定义 realm 类
 * author : sunpanhu
 * createTime : 2018/4/20 上午11:06
 */
public class MyRealm3 implements Realm {
    public String getName() {
        return "myrealm3";
    }

    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken; //仅支持UsernamePasswordToken类型的Token
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();  //得到用户名
        String password = new String((char[])token.getCredentials()); //得到密码

        if(!"zhang".equals(username)) {
            throw new UnknownAccountException(); //如果用户名错误
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //如果密码错误
        }

        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username + "@163.com", password, getName());
    }
}
