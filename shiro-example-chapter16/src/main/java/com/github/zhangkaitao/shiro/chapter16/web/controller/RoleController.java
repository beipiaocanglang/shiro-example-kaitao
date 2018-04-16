package com.github.zhangkaitao.shiro.chapter16.web.controller;

import com.github.zhangkaitao.shiro.chapter16.entity.Role;
import com.github.zhangkaitao.shiro.chapter16.service.ResourceService;
import com.github.zhangkaitao.shiro.chapter16.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 关于用户角色的操作
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取所有角色信息 点击左侧 角色管理 跳转到右侧角色首页
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:51
     * @return
     */
    @RequiresPermissions("role:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("roleList", roleService.findAll());
        return "role/list";
    }

    /**
     * 新增角色 跳转到 新增页面(同时获取所有资源信息 用于页面的回显选择)
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:10
     * @return
     */
    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        //获取所有资源
        setCommonData(model);
        model.addAttribute("role", new Role());
        model.addAttribute("op", "新增");
        return "role/edit";
    }

    /**
     * 新增角色 新增按钮
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:11
     * @return
     */
    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Role role, RedirectAttributes redirectAttributes) {
        roleService.createRole(role);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/role";
    }

    /**
     * 修改 跳转到修改页面
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:19
     * @return
     */
    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        //获取所有资源
        setCommonData(model);
        //根据角色id获取角色信息
        model.addAttribute("role", roleService.findOne(id));
        model.addAttribute("op", "修改");
        return "role/edit";
    }

    /**
     * 修改 修改页面点击 修改 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:16
     * @return
     */
    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Role role, RedirectAttributes redirectAttributes) {
        roleService.updateRole(role);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/role";
    }

    /**
     * 删除角色 根据当前角色id 查询角色信息用于角色删除页面的回显
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:20
     * @return
     */
    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        //获取所有资源
        setCommonData(model);
        model.addAttribute("role", roleService.findOne(id));
        model.addAttribute("op", "删除");
        return "role/edit";
    }

    /**
     * 删除角色 根据当前角色id删除
     * author : sunpanhu
     * createTime : 2018/4/16 上午11:22
     * @return
     */
    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        roleService.deleteRole(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/role";
    }

    //公共的查询所有资源的方法
    private void setCommonData(Model model) {

        model.addAttribute("resourceList", resourceService.findAll());
    }
}
