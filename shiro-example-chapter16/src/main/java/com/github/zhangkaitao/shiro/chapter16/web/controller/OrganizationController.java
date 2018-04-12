package com.github.zhangkaitao.shiro.chapter16.web.controller;

import com.github.zhangkaitao.shiro.chapter16.entity.Organization;
import com.github.zhangkaitao.shiro.chapter16.service.OrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 关于组织机构的操作
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    /**
     * 组织结构首页
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:18
     * @return
     */
    @RequiresPermissions("organization:view")
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "organization/index";
    }

    /**
     * 默认加载右侧组织结构首页时 发送请求加载对应的数据
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:20
     * @return
     */
    @RequiresPermissions("organization:view")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String showTree(Model model) {
        model.addAttribute("organizationList", organizationService.findAll());
        return "organization/tree";
    }

    /**
     * 点击节点时根据组织id查询组织数据 获取节点详细信息 最右侧组织节点详细信息
     * 刷新树形
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:07
     * @return
     */
    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{id}/maintain", method = RequestMethod.GET)
    public String showMaintainForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("organization", organizationService.findOne(id));
        return "organization/maintain";
    }

    /**
     * 添加组织结构的子节点 点击"添加子节点"时 获取当前节点信息
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:20
     * @return
     */
    @RequiresPermissions("organization:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.GET)
    public String showAppendChildForm(@PathVariable("parentId") Long parentId, Model model) {
        //根据当前节点的父节点id查询节点信息
        Organization parent = organizationService.findOne(parentId);
        model.addAttribute("parent", parent);

        Organization child = new Organization();
        child.setParentId(parentId);
        child.setParentIds(parent.makeSelfAsParentIds());

        model.addAttribute("child", child);
        model.addAttribute("op", "新增");
        //跳转到新增页面
        return "organization/appendChild";
    }
    /**
     * 添加组织结构的子节点 新增子节点页面的 点击 "新增子节点(保存)" 时
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:30
     * @return
     */
    @RequiresPermissions("organization:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.POST)
    public String create(Organization organization) {
        //添加子节点
        organizationService.createOrganization(organization);
        //重定向到本类中的成功请求中(最下面)
        return "redirect:/organization/success";
    }

    /**
     * 修改组织结构节点名称
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:30
     * @return
     */
    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Organization organization, RedirectAttributes redirectAttributes) {
        //更新节点信息
        organizationService.updateOrganization(organization);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        //重定向到本类中的成功请求中(最下面)
        return "redirect:/organization/success";
    }

    /**
     * 删除子节点
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:33
     * @return
     */
    @RequiresPermissions("organization:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        organizationService.deleteOrganization(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/organization/success";
    }

    /**
     * 点击节点详情中的『移动节点』
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:53
     * @return
     */
    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{sourceId}/move", method = RequestMethod.GET)
    public String showMoveForm(@PathVariable("sourceId") Long sourceId, Model model) {
        //根据id获取组织信息
        Organization source = organizationService.findOne(sourceId);
        model.addAttribute("source", source);
        //查询所有不包含当前组织信息的其他所有组织信息 用于在move页面中回显要移动到的节点
        model.addAttribute("targetList", organizationService.findAllWithExclude(source));
        return "organization/move";
    }
    /**
     * 执行移动页面的中的"移动"按钮
     * author : sunpanhu
     * createTime : 2018/4/12 上午11:08
     * @return
     */
    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{sourceId}/move", method = RequestMethod.POST)
    public String move(@PathVariable("sourceId") Long sourceId, @RequestParam("targetId") Long targetId) {
        //获取当前组织信息
        Organization source = organizationService.findOne(sourceId);
        //获取要移动到的组织信息
        Organization target = organizationService.findOne(targetId);
        //执行移动操作
        organizationService.move(source, target);
        return "redirect:/organization/success";
    }

    /**
     * 跳转到成功页面
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:34
     * @return
     */
    @RequiresPermissions("organization:view")
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "organization/success";
    }
}
