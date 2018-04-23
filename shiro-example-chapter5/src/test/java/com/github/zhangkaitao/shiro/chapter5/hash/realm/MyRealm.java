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
 * 自定义Realm
 * Shiro默认提供了
 *      PasswordService的实现类DefaultPasswordService；
 *      CredentialsMatcher的实现类 PasswordMatcher 和 HashedCredentialsMatcher（更强大）
 *
 * DefaultPasswordService配合PasswordMatcher实现简单的密码加密与验证服务
 * author : sunpanhu
 * createTime : 2018/4/23 下午3:00
 */
public class MyRealm extends AuthorizingRealm {
    //为了方便，直接注入一个passwordService来加密密码，实际使用时需要在Service层使用passwordService加密密码并存到数据库
    private PasswordService passwordService;

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String password = passwordService.encryptPassword("123");
        SimpleAuthenticationInfo wu = new SimpleAuthenticationInfo("wu", password, getName());
        return wu;
    }
}
