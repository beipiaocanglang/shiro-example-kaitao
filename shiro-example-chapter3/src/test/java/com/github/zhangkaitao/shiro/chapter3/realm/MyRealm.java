package com.github.zhangkaitao.shiro.chapter3.realm;

import com.github.zhangkaitao.shiro.chapter3.permission.BitPermission;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm
 * 此时我们继承AuthorizingRealm而不是实现Realm接口；
 * 推荐使用AuthorizingRealm，
 * 因为：
 *      AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)：表示获取身份验证信息；
 *      AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)：表示根据用户身份获取授权信息。
 * 这种方式的好处是当只需要身份验证 和 获取身份授权时。
 * author : sunpanhu
 * createTime : 2018/4/20 下午3:50
 */
public class MyRealm extends AuthorizingRealm {

    /**
     * 授权
     * author : sunpanhu
     * createTime : 2018/3/27 下午6:14
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("role1");
        authorizationInfo.addRole("role2");
        authorizationInfo.addObjectPermission(new BitPermission("+user1+10"));
        authorizationInfo.addObjectPermission(new WildcardPermission("user1:*"));
        authorizationInfo.addStringPermission("+user2+10");
        authorizationInfo.addStringPermission("user2:*");
        return authorizationInfo;
    }

    /**
     * 认证
     * author : sunpanhu
     * createTime : 2018/3/27 下午6:14
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();  //得到用户名
        String password = new String((char[])token.getCredentials()); //得到密码
        if(!"zhang".equals(username)) {
            throw new UnknownAccountException(); //如果用户名错误
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //如果密码错误
        }
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        SimpleAuthenticationInfo sati = new SimpleAuthenticationInfo(username, password, getName());
        return sati;
    }
}
