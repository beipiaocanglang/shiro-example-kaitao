package com.github.zhangkaitao.shiro.chapter16.web.controller;

import com.github.zhangkaitao.shiro.chapter16.entity.Organization;
import com.github.zhangkaitao.shiro.chapter16.entity.Role;
import com.github.zhangkaitao.shiro.chapter16.entity.User;
import com.github.zhangkaitao.shiro.chapter16.service.OrganizationService;
import com.github.zhangkaitao.shiro.chapter16.service.RoleService;
import com.github.zhangkaitao.shiro.chapter16.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 关于用户操作
 * author : sunpanhu
 * createTime : 2018/4/9 上午11:37
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RoleService roleService;

    /**
     * 点击 左侧 用户管理 获取用户列表
     * 拥有user:view权限的用户才能访问次请求
     * 无条件查询所有用户 - 用户管理
     * author : sunpanhu
     * createTime : 2018/4/9 上午11:31
     * @param model
     * @return
     */
    @RequiresPermissions("user:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    /**
     * 用户新增 跳到新增页面
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:32
     * @return 
     */
    @RequiresPermissions("user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        //查询所有组织结构 和 所有角色列表
        setCommonData(model);
        model.addAttribute("user", new User());
        model.addAttribute("op", "新增");
        return "user/edit";
    }

    /**
     * 新增用户 新增页面的 新增按钮
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:35
     * @return
     */
    @RequiresPermissions("user:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(User user, RedirectAttributes redirectAttributes) {
        userService.createUser(user);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/user";
    }

    /**
     * 修改用户信息 获取用户信息跳转编辑页面
     * author : sunpanhu
     * createTime : 2018/4/16 下午12:17
     * @return
     */
    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        //查询所有组织结构 和 所有角色列表
        User user = userService.findOne(id);
        setCommonData(model);
        model.addAttribute("user", user);
        model.addAttribute("op", "修改");
        return "user/edit";
    }

    /**
     * 修改用户信息 点击 修改
     * author : sunpanhu
     * createTime : 2018/4/16 下午12:17
     * @return
     */
    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/user";
    }

    /**
     * 删除用户 获取用户信息 跳转到删除页面
     * author : sunpanhu
     * createTime : 2018/4/16 下午12:18
     * @return
     */
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        //查询所有组织结构 和 所有角色列表
        User user = userService.findOne(id);
        setCommonData(model);
        model.addAttribute("user", user);
        model.addAttribute("op", "删除");
        return "user/edit";
    }

    /**
     * 删除用户 点击 删除 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 下午12:18
     * @return
     */
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/user";
    }

    /**
     * 修改用户密码 点击 改密 获取当前用户信息
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:27
     * @return
     */
    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("op", "修改密码");
        return "user/changePassword";
    }

    /**
     * 修改密码页面 点击 修改密码 提交
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:28
     * @return
     */
    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Long id, String newPassword, RedirectAttributes redirectAttributes) {
        userService.changePassword(id, newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        return "redirect:/user";
    }

    //查询所有组织结构 和 所有角色列表
    private void setCommonData(Model model) {
        List<Organization> organizationList = organizationService.findAll();
        List<Role> roleList = roleService.findAll();
        model.addAttribute("organizationList", organizationList);
        model.addAttribute("roleList", roleList);
    }
}
