package com.github.zhangkaitao.shiro.chapter17.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后端数据维护控制器 - 首页
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:21
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        //跳转到 祸胎数据维护页面
        return "index";
    }
}
