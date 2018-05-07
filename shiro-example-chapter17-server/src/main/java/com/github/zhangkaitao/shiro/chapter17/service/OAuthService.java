package com.github.zhangkaitao.shiro.chapter17.service;

/**
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:14
 */
public interface OAuthService {
    /**
     * 添加 auth code
     * @param authCode
     * @param username
     */
    void addAuthCode(String authCode, String username);

    /**
     * 添加 access token
     * @param accessToken
     * @param username
     */
    void addAccessToken(String accessToken, String username);

    /**
     * 验证auth code是否有效
     * @param authCode
     * @return
     */
    boolean checkAuthCode(String authCode);

    /**
     * 验证access token是否有效
     * @param accessToken
     * @return
     */
    boolean checkAccessToken(String accessToken);

    /**
     * 根据authCode查询用户名
     * @param authCode
     * @return
     */
    String getUsernameByAuthCode(String authCode);

    /**
     * 根据accessToken查询用户名
     * @param accessToken
     * @return
     */
    String getUsernameByAccessToken(String accessToken);

    /**
     * auth code / access token 过期时间
     * @return
     */
    long getExpireIn();

    /**
     * 根据clientId查询client信息
     * @param clientId
     * @return
     */
    boolean checkClientId(String clientId);

    /**
     * 检查客户端安全KEY是否正确
     * @param clientSecret
     * @return
     */
    boolean checkClientSecret(String clientSecret);
}
