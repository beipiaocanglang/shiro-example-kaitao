package com.github.zhangkaitao.shiro.chapter22.web.controller;

import com.github.zhangkaitao.shiro.chapter22.entity.Resource;
import com.github.zhangkaitao.shiro.chapter22.entity.User;
import com.github.zhangkaitao.shiro.chapter22.service.ResourceService;
import com.github.zhangkaitao.shiro.chapter22.service.UserService;
import com.github.zhangkaitao.shiro.chapter22.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

/**
 * 关于首页操作的controller
 * author : sunpanhu
 * createTime : 2018/5/16 上午10:15
 */
@Controller
public class IndexController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        List<Resource> menus = resourceService.findMenus(permissions);
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
