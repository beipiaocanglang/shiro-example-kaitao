package com.github.zhangkaitao.shiro.chapter17.web.controller;

import com.github.zhangkaitao.shiro.chapter17.entity.User;
import com.github.zhangkaitao.shiro.chapter17.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 后端数据维护控制器 - 用户端操作
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:21
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取服务端的用户首页
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:20
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "user/list";
    }

    /**
     * 新增用户 点击新增用户 跳转到新增用户首页
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:21
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("op", "新增");
        return "user/edit";
    }

    /**
     * 点击 新增用户页面的 新增按钮
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:22
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(User user, RedirectAttributes redirectAttributes) {
        userService.createUser(user);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/user";
    }

    /**
     * 修改用户信息 点击修改 跳转到用户编辑页面 获取用户信息
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:22
     * @return
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改");
        return "user/edit";
    }

    /**
     * 提交编辑 点击编辑页面的 修改 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:23
     * @return
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/user";
    }

    /**
     * 删除用户 跳转到删除用户页面的 获取用户信息
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:24
     * @return
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "删除");
        return "user/edit";
    }

    /**
     * 提交删除 点击删除页面的 删除 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:24
     * @return
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/user";
    }

    /**
     * 修改密码 跳转到修改密码页面
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:25
     * @return
     */
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改密码");
        return "user/changePassword";
    }

    /**
     * 提交修改密码 点击修改密码页面的 修改密码 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:25
     * @return
     */
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Long id, String newPassword, RedirectAttributes redirectAttributes) {
        userService.changePassword(id, newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        return "redirect:/user";
    }
}
