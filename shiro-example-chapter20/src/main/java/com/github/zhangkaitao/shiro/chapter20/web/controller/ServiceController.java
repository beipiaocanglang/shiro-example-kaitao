package com.github.zhangkaitao.shiro.chapter20.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟服务控制器
 * author : sunpanhu
 * createTime : 2018/4/17 下午4:38
 */
@RestController
public class ServiceController {

    //当访问/hello服务时，需要传入param1、param2两个请求参数
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello1(String[] param1, String param2) {
        String hello = "hello" + param1[0] + param1[1] + param2;
        return hello;
    }
}
