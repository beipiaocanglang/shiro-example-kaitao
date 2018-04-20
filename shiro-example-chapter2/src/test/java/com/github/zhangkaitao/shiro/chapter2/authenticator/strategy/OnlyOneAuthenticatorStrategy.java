package com.github.zhangkaitao.shiro.chapter2.authenticator.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.realm.Realm;
import java.util.Collection;

/**
 * 自定义AuthenticationStrategy
 * 因为每个AuthenticationStrategy实例都是无状态的，所有每次都通过接口将相应的认证信息传入下一次流程；
 * 通过此自定义的可以进行如合并/返回第一个验证成功的认证信息。
 * author : sunpanhu
 * createTime : 2018/4/20 上午11:08
 */
public class OnlyOneAuthenticatorStrategy extends AbstractAuthenticationStrategy {

    //在所有Realm验证之前调用
    @Override
    public AuthenticationInfo beforeAllAttempts(Collection<? extends Realm> realms, AuthenticationToken token) throws AuthenticationException {
        //返回一个权限的认证信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        return simpleAuthenticationInfo;
    }

    //在每个Realm之前调用
    @Override
    public AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        return aggregate;//返回之前合并的
    }

    //在每个Realm之后调用
    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
        AuthenticationInfo info;
        if (singleRealmInfo == null) {
            info = aggregateInfo;
        } else {
            if (aggregateInfo == null) {
                info = singleRealmInfo;
            } else {
                info = merge(singleRealmInfo, aggregateInfo);
                if(info.getPrincipals().getRealmNames().size() > 1) {
                    System.out.println(info.getPrincipals().getRealmNames());
                    throw new AuthenticationException("Authentication token of type [" + token.getClass() + "] " +
                            "could not be authenticated by any configured realms.  Please ensure that only one realm can " +
                            "authenticate these tokens.");
                }
            }
        }
        return info;
    }

    //在所有Realm之后调用
    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        return aggregate;
    }
}
