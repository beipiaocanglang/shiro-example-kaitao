package com.github.zhangkaitao.shiro.chapter20;

import com.github.zhangkaitao.shiro.chapter20.codec.HmacSHA256Utils;
import junit.framework.Assert;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 客户端测试类
 * author : sunpanhu
 * createTime : 2018/5/10 上午9:45
 */
public class ClientTest {
    private static Server server;
    private RestTemplate restTemplate = new RestTemplate();

    //为了方便 ，使用内嵌jetty服务器启动服务端：
    //在整个测试开始之前开启服务器，整个测试结束时关闭服务器
    @BeforeClass
    public static void beforeClass() throws Exception {
        //创建一个server
        server = new Server(8081);
        WebAppContext context = new WebAppContext();
        String webapp = "shiro-example-chapter20/src/main/webapp";
        context.setDescriptor(webapp + "/WEB-INF/web.xml");  //指定web.xml配置文件
        context.setResourceBase(webapp);  //指定webapp目录
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        server.setHandler(context);
        server.start();
    }

    /**
     * 测试成功情况
     * 对请求参数生成消息摘要后带到参数中传递给服务器端，服务器端验证通过后访问相应服务，然后返回数据。
     * author : sunpanhu
     * createTime : 2018/4/17 下午4:53
     */
    @Test
    public void testServiceHelloSuccess() {
        String username = "admin";
        String param11 = "param11";
        String param12 = "param12";
        String param2 = "param2";
        String key = "dadadswdewq2ewdwqdwadsadasd";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        //注意参数顺序
        params.add(Constants.PARAM_USERNAME, username);
        params.add("param1", param11);
        params.add("param1", param12);
        params.add("param2", param2);
        params.add(Constants.PARAM_DIGEST, HmacSHA256Utils.digest(key, params));//编码

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/chapter20/hello").queryParams(params).build().toUriString();

        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        String param = "hello" + param11 + param12 + param2;
        Object returnParam = responseEntity.getBody();
        Assert.assertEquals(param, returnParam);
    }

    /**
     * 测试失败情况
     * 在生成请求参数消息摘要后，篡改了参数内容，服务器端接收后进行重新生成消息摘要发现不一样，报401错误状态码。
     * author : sunpanhu
     * createTime : 2018/4/17 下午4:53
     */
    @Test
    public void testServiceHelloFail() {
        String username = "admin";
        String param11 = "param11";
        String param12 = "param12";
        String param2 = "param2";
        String key = "dadadswdewq2ewdwqdwadsadasd";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add(Constants.PARAM_USERNAME, username);
        params.add("param1", param11);
        params.add("param1", param12);
        params.add("param2", param2);
        params.add(Constants.PARAM_DIGEST, HmacSHA256Utils.digest(key, params));
        params.set("param2", param2 + "1");

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/chapter20/hello").queryParams(params).build().toUriString();

        try {
            restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusCode());
            Assert.assertEquals("login error", e.getResponseBodyAsString());
        }
    }

    //在整个测试开始之前开启服务器，整个测试结束时关闭服务器
    @AfterClass
    public static void afterClass() throws Exception {
        server.stop(); //当测试结束时停止服务器
    }
}
