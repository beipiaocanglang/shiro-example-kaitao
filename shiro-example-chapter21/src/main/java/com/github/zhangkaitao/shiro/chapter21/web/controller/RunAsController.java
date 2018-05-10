package com.github.zhangkaitao.shiro.chapter21.web.controller;

import com.github.zhangkaitao.shiro.chapter21.entity.User;
import com.github.zhangkaitao.shiro.chapter21.service.UserRunAsService;
import com.github.zhangkaitao.shiro.chapter21.service.UserService;
import com.github.zhangkaitao.shiro.chapter21.web.bind.annotation.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 授权并切换用户操作
 * 该控制器完成：授予身份/回收身份/切换身份功能。
 * author : sunpanhu
 * createTime : 2018/4/18 上午9:58
 */
@Controller
@RequestMapping("/runas")
public class RunAsController {

    @Autowired
    private UserRunAsService userRunAsService;

    @Autowired
    private UserService userService;

    /**
     * 展示当前用户能切换到身份列表，及授予给其他人的身份列表
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:21
     * @return
     */
    @RequestMapping
    public String runasList(@CurrentUser User loginUser, Model model) {

        List<Long> fromUserIds = userRunAsService.findFromUserIds(loginUser.getId());
        List<Long> toUserIds = userRunAsService.findToUserIds(loginUser.getId());

        model.addAttribute("fromUserIds", fromUserIds);
        model.addAttribute("toUserIds", toUserIds);

        List<User> allUsers = userService.findAll();
        allUsers.remove(loginUser);
        model.addAttribute("allUsers", allUsers);

        Subject subject = SecurityUtils.getSubject();
        //表示当前用户是否是RunAs过的用户，即已经切换身份了
        boolean runAs = subject.isRunAs();
        model.addAttribute("isRunas", runAs);
        if(runAs) {
            //得到切换身份之前的身份，一个用户可以切换很多次身份，之前的身份使用栈数据结构来存储；
            String previousUsername = (String)subject.getPreviousPrincipals().getPrimaryPrincipal();
            model.addAttribute("previousUsername", previousUsername);
        }

        return "runas";
    }

    /**
     * 授予身份
     * 把当前用户身份授予给另一个用户，这样另一个用户可以切换身份到该用户
     * 注意：
     *      自己不能授予身份给自己
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:23
     * @return
     */
    @RequestMapping("/grant/{toUserId}")
    public String grant(@CurrentUser User loginUser, @PathVariable("toUserId") Long toUserId,
                        RedirectAttributes redirectAttributes) {

        if(loginUser.getId().equals(toUserId)) {
            redirectAttributes.addFlashAttribute("msg", "自己不能切换到自己的身份");
            return "redirect:/runas";
        }

        //调用service层把当前登录用户的身份授予给相应的用户
        userRunAsService.grantRunAs(loginUser.getId(), toUserId);
        redirectAttributes.addFlashAttribute("msg", "操作成功");
        return "redirect:/runas";
    }

    /**
     * 回收身份
     * 把授予给某个用户的身份回收回来。
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:24
     * @return
     */
    @RequestMapping("/revoke/{toUserId}")
    public String revoke(@CurrentUser User loginUser, @PathVariable("toUserId") Long toUserId,
                        RedirectAttributes redirectAttributes) {

        userRunAsService.revokeRunAs(loginUser.getId(), toUserId);
        redirectAttributes.addFlashAttribute("msg", "操作成功");

        return "redirect:/runas";
    }

    /**
     * 切换身份
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:25
     * @return
     */
    @RequestMapping("/switchTo/{switchToUserId}")
    public String switchTo(@CurrentUser User loginUser, @PathVariable("switchToUserId") Long switchToUserId,
                           RedirectAttributes redirectAttributes) {

        Subject subject = SecurityUtils.getSubject();
        //1、首先根据switchToUserId查找到要切换到的身份
        User switchToUser = userService.findOne(switchToUserId);
        //2、然后通过UserRunAsService. exists()判断当前登录用户是否可以切换到该身份
        if(loginUser.equals(switchToUser)) {
            redirectAttributes.addFlashAttribute("msg", "自己不能切换到自己的身份");
            return "redirect:/runas";
        }
        //查看当前用户和要切换到的用户之间是否存在可切换关系
        boolean exists = userRunAsService.exists(switchToUserId, loginUser.getId());
        if(switchToUser == null || !exists) {
            redirectAttributes.addFlashAttribute("msg", "对方没有授予您身份，不能切换");
            return "redirect:/runas";
        }

        //3、通过Subject.runAs()切换到该身份
        subject.runAs(new SimplePrincipalCollection(switchToUser.getUsername(), ""));

        redirectAttributes.addFlashAttribute("msg", "操作成功");
        redirectAttributes.addFlashAttribute("needRefresh", "true");
        return "redirect:/runas";
    }

    /**
     * 切换到上一个身份
     * 注意：
     *      此处注意的是我们可以切换多次身份，如A切换到B，然后再切换到C；
     *      那么需要调用两次 Subject.releaseRunAs() 才能切换会A；
     *      即内部使用栈数据结构存储着切换过的用户；
     *      Subject.getPreviousPrincipals() 得到上一次切换到的身份，比如当前是C；
     *      那么调用该API将得到B的身份。
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:27
     * @return
     */
    @RequestMapping("/switchBack")
    public String switchBack(RedirectAttributes redirectAttributes) {

        Subject subject = SecurityUtils.getSubject();

        if(subject.isRunAs()) {
            //通过Subject.releaseRunAs()切换会上一个身份；
            subject.releaseRunAs();
        }
        redirectAttributes.addFlashAttribute("msg", "操作成功");
        redirectAttributes.addFlashAttribute("needRefresh", "true");
        return "redirect:/runas";
    }
}
