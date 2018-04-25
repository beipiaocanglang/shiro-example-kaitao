package com.github.zhangkaitao.shiro.chapter6.credentials;

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
 * 校验密码输入错误的次数
 * 校验加密、机密对比
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Ehcache passwordRetryCache;

    //密码重试缓存
    //本类的无参构造
    public RetryLimitHashedCredentialsMatcher() {
        URL resource = CacheManager.class.getClassLoader().getResource("ehcache.xml");
        CacheManager cacheManager = CacheManager.newInstance(resource);
        //passwordRetryCache 这个key是缓存配置文件中的对应的name值
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * 判断用户输入的密码是否正确
     * 如果错误 则判断用户输入的密码错误次数
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取用户名
        String username = (String)token.getPrincipal();
        //retry count + 1
        //获取ehcache缓存中的用户信息信息
        Element element = passwordRetryCache.get(username);
        //如果缓存中没有用户信息则创建用户信息存入缓存
        if(element == null) {
            element = new Element(username , new AtomicInteger(0));
            passwordRetryCache.put(element);
        }

        //获取用户输入的次数
        AtomicInteger retryCount = (AtomicInteger)element.getObjectValue();
        //如果用户输入错误次数大于5 抛出异常
        if(retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        //校验用户输入的密码是否正确
        boolean matches = super.doCredentialsMatch(token, info);
        //密码正确  则登录成功 同时清除缓存中的用户信息
        if(matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
