package com.github.zhangkaitao.shiro.chapter18.oauth2;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 该filter的作用类似于FormAuthenticationFilter用于oauth2客户端的身份验证控制；
 * 如果当前用户还没有身份验证，首先会判断url中是否有code（服务端返回的auth code），如果没有则重定向到服务端进行登录并授权，然后返回auth code；
 * 接着OAuth2AuthenticationFilter会用auth code创建OAuth2Token，然后提交给Subject.login进行登录；
 * 接着OAuth2Realm会根据OAuth2Token进行相应的登录逻辑。
 * 该拦截器的作用：
 *      1、首先判断有没有服务端返回的error参数，如果有则直接重定向到失败页面；
 *      2、接着如果用户还没有身份验证，判断是否有auth code参数（即是不是服务端授权之后返回的），如果没有则重定向到服务端进行授权；
 *      3、否则调用executeLogin进行登录，通过auth code创建OAuth2Token提交给Subject进行登录；
 *      4、登录成功将回调onLoginSuccess方法重定向到成功页面；
 *      5、登录失败则回调onLoginFailure重定向到失败页面。
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:31
 */
public class OAuth2AuthenticationFilter extends AuthenticatingFilter {

    //oauth2 authc code参数名
    private String authcCodeParam = "code";
    //客户端id
    private String clientId;
    //服务器端登录成功/失败后重定向到的客户端地址
    private String redirectUrl;
    //oauth2服务器响应类型
    private String responseType = "code";

    private String failureUrl;

    public void setAuthcCodeParam(String authcCodeParam) {
        this.authcCodeParam = authcCodeParam;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //获取code
        String code = httpRequest.getParameter(authcCodeParam);
        return new OAuth2Token(code);
    }

    /**
     * 请求之前会先执行
     * 表示是否允许访问；
     * mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     * author : sunpanhu
     * createTime : 2018/4/16 下午3:55
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }
    /**
     * 表示当访问拒绝时是否已经处理了；
     * 如果返回true表示需要继续处理；
     * 如果返回false表示该拦截器实例已经处理了，将直接返回即可，（比如重定向到另一个页面）
     * author : sunpanhu
     * createTime : 2018/4/16 下午3:57
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        //如果服务端返回了错误
        if(!StringUtils.isEmpty(error)) {
            WebUtils.issueRedirect(request, response, failureUrl + "?error=" + error + "error_description=" + errorDescription);
            return false;
        }

        Subject subject = getSubject(request, response);
        //获取身份认证信息
        boolean authenticated = subject.isAuthenticated();
        //如果用户没有身份验证
        if(!authenticated) {
            String authCode = request.getParameter(authcCodeParam);
            //用户没有auth code，
            if(StringUtils.isEmpty(authCode)) {
                //重定向到服务端授权
                saveRequestAndRedirectToLogin(request, response);
                //如果返回false表示该拦截器实例已经处理了，将直接返回即可，（比如重定向到另一个页面）
                return false;
            }
        }

        //执行上面的 AuthenticationToken 方法
        boolean b = executeLogin(request, response);
        return b;
    }

    /**
     * 登录成功将回调onLoginSuccess方法重定向到成功页面；
     * author : sunpanhu
     * createTime : 2018/4/16 下午3:58
     * @return
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        issueSuccessRedirect(request, response);
        return false;
    }

    /**
     * 登录失败则回调onLoginFailure重定向到失败页面。
     * author : sunpanhu
     * createTime : 2018/4/16 下午3:59
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, failureUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
