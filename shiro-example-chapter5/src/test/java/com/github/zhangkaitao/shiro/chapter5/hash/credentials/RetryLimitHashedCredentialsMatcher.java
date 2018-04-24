package com.github.zhangkaitao.shiro.chapter5.hash.credentials;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 密码重试次数限制
 * 如在1个小时内密码最多重试5次，如果尝试次数超过5次就锁定1小时，
 * 1小时后可再次重试，如果还是重试失败，可以锁定如1天，
 * 以此类推，防止密码被暴力破解。我们通过继承HashedCredentialsMatcher，
 * 且使用Ehcache记录重试次数和超时时间。
 * author : sunpanhu
 * createTime : 2018/4/23 下午2:37
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Ehcache passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher() {
        //获取ehcache.xml配置文件的本地磁盘路径
        URL resource = CacheManager.class.getClassLoader().getResource("ehcache.xml");
        CacheManager cacheManager = CacheManager.newInstance(resource);
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * 如果密码输入正确清除cache中的记录；否则cache中的重试次数+1，如果超出5次那么抛出异常表示超出重试次数了。
     * author : sunpanhu
     * createTime : 2018/4/2 上午11:11
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
        Element element = passwordRetryCache.get(username);
        if(element == null) {
            element = new Element(username , new AtomicInteger(0));
            passwordRetryCache.put(element);
        }
        AtomicInteger retryCount = (AtomicInteger)element.getObjectValue();
        //如果密码错误5次以上 抛出异常
        if(retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        //如果用户输入的密码在5次之内输入正确了 就清楚缓存中的用户名
        if(matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
