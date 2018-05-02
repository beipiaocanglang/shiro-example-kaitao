package com.github.zhangkaitao.shiro.chapter11.realm;

import com.github.zhangkaitao.shiro.chapter11.BaseTest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.junit.Test;

/**
 * 测试缓存的存储、清空案例
 */
public class UserRealmTest extends BaseTest {

    /**
     * 退出时请解除绑定Subject到线程 否则对下次测试造成影响
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:00
     */
    @Override
    public void tearDown() throws Exception {
        userService.changePassword(u1.getId(), password);
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
        userRealm.clearCachedAuthenticationInfo(subject().getPrincipals());

        super.tearDown();
    }

    /**
     * 首先登录成功（此时会缓存相应的AuthenticationInfo），然后修改密码；
     * 此时密码就变了；
     * 接着需要调用Realm的clearCachedAuthenticationInfo方法清空之前缓存的AuthenticationInfo；
     * 否则下次登录时还会获取到修改密码之前的那个AuthenticationInfo；
     * author : sunpanhu
     * createTime : 2018/4/4 下午2:53
     */
    @Test
    public void testClearCachedAuthenticationInfo() {
        login(u1.getUsername(), password);
        userService.changePassword(u1.getId(), password + "1");

        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
        //认证时清理缓存
        userRealm.clearCachedAuthenticationInfo(subject().getPrincipals());

        login(u1.getUsername(), password + "1");
    }

    /**
     * 清空之前缓存的AuthorizationInfo
     *
     * 同上面的认证流程
     *
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:02
     */
    @Test
    public void testClearCachedAuthorizationInfo() {
        login(u1.getUsername(), password);
        subject().checkRole(r1.getRole());
        userService.correlationRoles(u1.getId(), r2.getId());

        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
        userRealm.clearCachedAuthorizationInfo(subject().getPrincipals());

        subject().checkRole(r2.getRole());
    }

    /**
     * 同时调用clearCachedAuthenticationInfo和clearCachedAuthorizationInfo，
     * 清空AuthenticationInfo和AuthorizationInfo。
     * author : sunpanhu
     * createTime : 2018/4/4 下午3:02
     */
    @Test
    public void testClearCache() {
        login(u1.getUsername(), password);
        subject().checkRole(r1.getRole());

        userService.changePassword(u1.getId(), password + "1");
        userService.correlationRoles(u1.getId(), r2.getId());

        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();

        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
        userRealm.clearCache(subject().getPrincipals());

        login(u1.getUsername(), password + "1");
        subject().checkRole(r2.getRole());
    }
}
