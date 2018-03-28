package com.github.zhangkaitao.shiro.chapter5.hash.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Shiro默认提供了
 *      PasswordService实现DefaultPasswordService；
 *      CredentialsMatcher实现PasswordMatcher及HashedCredentialsMatcher（更强大）
 *
 * DefaultPasswordService配合PasswordMatcher实现简单的密码加密与验证服务
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-27
 * <p>Version: 1.0
 */
public class MyRealm extends AuthorizingRealm {
    //为了方便，直接注入一个passwordService来加密密码，实际使用时需要在Service层使用passwordService加密密码并存到数据库
    private PasswordService passwordService;

    public void setPasswordService(PasswordService passwordService) {

        this.passwordService = passwordService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        return new SimpleAuthenticationInfo("wu", passwordService.encryptPassword("123"), getName());
    }
}
