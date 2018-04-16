package com.github.zhangkaitao.shiro.chapter17.web.controller;

import com.github.zhangkaitao.shiro.chapter17.Constants;
import com.github.zhangkaitao.shiro.chapter17.service.OAuthService;
import com.github.zhangkaitao.shiro.chapter17.service.UserService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;

/**
 * 访问令牌控制器
 * 作用：
 *      1、首先通过如http://localhost:8080/chapter17-server/accessToken，POST提交如下数据：client_id= c1ebe466-1cdc-4bd3-ab69-77c3561b9dee& client_secret= d8346ea2-6017-43ed-ad68-19c0f971738b&grant_type=authorization_code&code=828beda907066d058584f37bcfd597b6&redirect_uri=http://localhost:9080/chapter17-client/oauth2-login访问；
 *      2、该控制器会验证client_id、client_secret、auth code的正确性，如果错误会返回相应的错误；
 *      3、如果验证通过会生成并返回相应的访问令牌access token。
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:25
 */
@RestController
public class AccessTokenController {

    @Autowired
    private OAuthService oAuthService;

    /**
     * 校验token
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:11
     * @return
     */
    @RequestMapping("/accessToken")
    public HttpEntity token(HttpServletRequest request) throws OAuthSystemException {

        try {
            //构建OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            //获取clientId
            String clientId = oauthRequest.getClientId();
            //根据clientId查询oAuth信息
            boolean oAuthInfo = oAuthService.checkClientId(clientId);
            //检查提交的客户端id是否正确 或者是否能查到
            if (!oAuthInfo) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                                .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                                .buildJSONMessage();
                ResponseEntity responseEntity = new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                return responseEntity;
            }

            String clientSecret = oauthRequest.getClientSecret();
            // 检查客户端安全KEY是否正确
            boolean isYouXiao = oAuthService.checkClientSecret(clientSecret);
            if (!isYouXiao) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                                .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                                .buildJSONMessage();
                ResponseEntity responseEntity = new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                return responseEntity;
            }

            String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
            // 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
            if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
                //验证auth code是否有效
                boolean oAuth = oAuthService.checkAuthCode(authCode);
                if (!oAuth) {
                    OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription("错误的授权码")
                            .buildJSONMessage();
                    ResponseEntity responseEntity = new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                    return responseEntity;
                }
            }

            //生成Access Token
            OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            final String accessToken = oauthIssuerImpl.accessToken();
            //通过AuthCode 获取用户名
            String usernameByAuthCode = oAuthService.getUsernameByAuthCode(authCode);
            //添加AccessToken
            oAuthService.addAccessToken(accessToken, usernameByAuthCode);
            //设置过期时间3600L
            String expireIn = String.valueOf(oAuthService.getExpireIn());
            //生成OAuth响应
            OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn(expireIn)
                    .buildJSONMessage();

            //根据OAuthResponse生成ResponseEntity
            ResponseEntity responseEntity = new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            return responseEntity;
        } catch (OAuthProblemException e) {
            //构建错误响应
            OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e).buildJSONMessage();
            ResponseEntity responseEntity = new ResponseEntity(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
            return responseEntity;
        }
    }
}
