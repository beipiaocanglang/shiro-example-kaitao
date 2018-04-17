package com.github.zhangkaitao.shiro.chapter20.realm;

import com.github.zhangkaitao.shiro.chapter20.codec.HmacSHA256Utils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm
 * 作用：
 *      用于认证
 * 解说：
 *      此处首先根据客户端传入的用户名获取相应的密钥，然后使用密钥对请求参数生成服务器端的消息摘要；
 *      然后与客户端的消息摘要进行匹配；
 *      如果匹配说明是合法客户端传入的；
 *      否则是非法的。
 * 弊端：
 *      这种方式是有漏洞的，一旦别人获取到该请求，可以重复请求；
 *      可以考虑之前介绍的解决方案。
 * author : sunpanhu
 * createTime : 2018/4/17 下午4:46
 */
public class StatelessRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        boolean instance = token instanceof StatelessToken;
        return instance;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //根据用户名查找角色，请根据需求实现
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        authorizationInfo.addRole("admin");

        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;
        String username = statelessToken.getUsername();
        //根据用户名获取密钥（和客户端的一样）调用下面的方法
        String key = getKey(username);
        //在服务器端生成客户端参数消息摘要
        String serverDigest = HmacSHA256Utils.digest(key, statelessToken.getParams());

        System.out.println(statelessToken.getClientDigest());
        System.out.println(serverDigest);

        //然后进行客户端消息摘要和服务器端消息摘要的匹配
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, serverDigest, getName());

        return simpleAuthenticationInfo;
    }

    //得到密钥，此处硬编码一个
    private String getKey(String username) {
        if("admin".equals(username)) {
            return "dadadswdewq2ewdwqdwadsadasd";
        }
        return null;
    }
}
