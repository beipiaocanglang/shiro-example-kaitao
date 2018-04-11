package com.github.zhangkaitao.shiro.chapter16.web.controller;

import com.github.zhangkaitao.shiro.chapter16.entity.Resource;
import com.github.zhangkaitao.shiro.chapter16.entity.User;
import com.github.zhangkaitao.shiro.chapter16.service.ResourceService;
import com.github.zhangkaitao.shiro.chapter16.service.UserService;
import com.github.zhangkaitao.shiro.chapter16.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

/**
 * 跳转到首页或欢迎页请求
 */
@Controller
public class IndexController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    /**
     * 登录成功后跳转到首页
     * 使用自定义注解 @CurrentUser User loginUser  可以拿到用户信息
     * author : sunpanhu
     * createTime : 2018/4/9 下午12:27
     * @param loginUser
     * @param model
     * @return java.lang.String
     */
    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        /*
         * 获取该用户的权限、角色、资源以及资源对应的菜单
         * 查询流程：
         *      使用自定义注解 @CurrentUser User loginUser  可以拿到用户信息
         *      1、根据用户名查找用户信息
         *          user = null：返回null
         *      2、用户信息不为null时 循环用户信息中的 多个角色id 分别查询对应的角色信息
         *      3、将查到的多个角色信息中对应的多个资源id封装成一个set集合
         *      4、将资源集合作为参数查询资源表(循环查询)，获取对应的权限封装成set集合返回
         *      5、根据权限集合查询对应的菜单
         *          5.1、获取所有资源
         *          5.2、循环资源 不是根节点、类型不是菜单的添加到封装的集合，
         *          5.3、循环权限集合和资源对应的集合对比 相等就添加到自定义的集合中
         *      6、放入Model中
         */
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        //根据权限查询权限对应的资源
        List<Resource> menus = resourceService.findMenus(permissions);

        model.addAttribute("menus", menus);
        return "index";
    }
    /**
     * 项目登录成功后 右侧显示的欢迎页信息
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:15
     * @return
     */
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
