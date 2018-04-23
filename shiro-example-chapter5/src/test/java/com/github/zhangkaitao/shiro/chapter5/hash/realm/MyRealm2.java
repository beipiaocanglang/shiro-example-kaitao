package com.github.zhangkaitao.shiro.chapter5.hash.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 自定义Realm
 * author : sunpanhu
 * createTime : 2018/4/23 下午3:01
 */
public class MyRealm2 extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证
     * 此处就是把步骤1(PasswordTest.java/testGeneratePassword)中生成的相应数据组装为SimpleAuthenticationInfo，
     * 通过SimpleAuthenticationInfo的credentialsSalt设置盐，HashedCredentialsMatcher会自动识别这个盐
     * author : sunpanhu
     * createTime : 2018/4/2 上午10:54
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = "liu"; //用户名及salt1
        String salt2 = "0072273a5d87322163795118fdd7c45e";
        String password = "be320beca57748ab9632c4121ccac0db"; //加密后的密码
        /*
         * HashedCredentialsMatcher(shiro-jdbc-hashedCredentialsMatcher.ini配置文件中的)会自动
         * 根据AuthenticationInfo的类型是否是SaltedAuthenticationInfo来获取credentialsSalt盐。
         */
        SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(username, password, getName());
        ai.setCredentialsSalt(ByteSource.Util.bytes(username+salt2)); //盐是用户名+随机数

        return ai;
    }
}
