package com.github.zhangkaitao.shiro.chapter17.service;

/**
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:14
 */
public interface OAuthService {
    //添加 auth code
    void addAuthCode(String authCode, String username);

    //添加 access token
    void addAccessToken(String accessToken, String username);

    //验证auth code是否有效
    boolean checkAuthCode(String authCode);

    //验证access token是否有效
    boolean checkAccessToken(String accessToken);

    String getUsernameByAuthCode(String authCode);

    String getUsernameByAccessToken(String accessToken);

    //auth code / access token 过期时间
    long getExpireIn();

    boolean checkClientId(String clientId);

    // 检查客户端安全KEY是否正确
    boolean checkClientSecret(String clientSecret);
}
