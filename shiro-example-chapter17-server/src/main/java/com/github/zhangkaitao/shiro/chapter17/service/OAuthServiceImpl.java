package com.github.zhangkaitao.shiro.chapter17.service;

import com.github.zhangkaitao.shiro.chapter17.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:14
 */
@Service
public class OAuthServiceImpl implements OAuthService {

    private Cache cache;

    @Autowired
    private ClientService clientService;

    @Autowired
    public OAuthServiceImpl(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("code-cache");
    }

    /**
     * 添加 auth code
     * @param authCode
     * @param username
     */
    public void addAuthCode(String authCode, String username) {
        cache.put(authCode, username);
    }

    /**
     * 添加 access token
     * @param accessToken
     * @param username
     */
    public void addAccessToken(String accessToken, String username) {
        cache.put(accessToken, username);
    }

    /**
     * 验证auth code是否有效
     * @param authCode
     * @return
     */
    public boolean checkAuthCode(String authCode) {
        Cache.ValueWrapper valueWrapper = cache.get(authCode);
        boolean resultAuthCode = valueWrapper != null;

        return resultAuthCode;
    }

    /**
     * 验证access token是否有效
     * @param accessToken
     * @return
     */
    public boolean checkAccessToken(String accessToken) {
        Cache.ValueWrapper valueWrapper = cache.get(accessToken);
        boolean accessTolen = valueWrapper != null;

        return accessTolen;
    }

    /**
     * 根据authCode查询用户名
     * @param authCode
     * @return
     */
    public String getUsernameByAuthCode(String authCode) {
        String resultAuthCode = (String)cache.get(authCode).get();
        return resultAuthCode;
    }

    /**
     * 根据accessToken查询用户名
     * @param accessToken
     * @return
     */
    public String getUsernameByAccessToken(String accessToken) {
        String resultAccessToken = (String)cache.get(accessToken).get();
        return resultAccessToken;
    }

    /**
     * auth code / access token 过期时间
     * @return
     */
    public long getExpireIn() {
        return 3600L;
    }

    /**
     * 根据clientId查询client信息
     * @param clientId
     * @return
     */
    public boolean checkClientId(String clientId) {
        Client client = clientService.findByClientId(clientId);
        boolean isClient = client != null;

        return isClient;
    }

    /**
     * 检查客户端安全KEY是否正确
     * @param clientSecret
     * @return
     */
    public boolean checkClientSecret(String clientSecret) {
        Client client = clientService.findByClientSecret(clientSecret);
        boolean isClient = client != null;

        return isClient;
    }
}
