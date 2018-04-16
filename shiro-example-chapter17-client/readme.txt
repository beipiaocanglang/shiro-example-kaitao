oauth2 client 讲解

1、首先访问http://localhost:9080/chapter17-client/，然后点击登录按钮进行登录，会跳到如下页面：

2、输入用户名进行登录并授权；

3、如果登录成功，服务端会重定向到客户端，即之前客户端提供的地址http://localhost:9080/chapter17-client/oauth2-login?code=473d56015bcf576f2ca03eac1a5bcc11，并带着auth code过去；

4、客户端的OAuth2AuthenticationFilter会收集此auth code，并创建OAuth2Token提交给Subject进行客户端登录；

5、客户端的Subject会委托给OAuth2Realm进行身份验证；此时OAuth2Realm会根据auth code换取access token，再根据access token获取受保护的用户信息；然后进行客户端登录。