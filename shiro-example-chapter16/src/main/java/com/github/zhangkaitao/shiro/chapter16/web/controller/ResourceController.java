package com.github.zhangkaitao.shiro.chapter16.web.controller;

import com.github.zhangkaitao.shiro.chapter16.entity.Resource;
import com.github.zhangkaitao.shiro.chapter16.service.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 关于资源的操作
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ModelAttribute("types")
    public Resource.ResourceType[] resourceTypes() {
        return Resource.ResourceType.values();
    }

    /**
     * 点击左侧 资源管理 查询所有的资源
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:10
     * @return
     */
    @RequiresPermissions("resource:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("resourceList", resourceService.findAll());
        //返回到资源列表页面
        return "resource/list";
    }

    /**
     * 根据父id 添加子节点前的查询 用于添加子节点页面的回显 添加子节点 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:18
     * @return
     */
    @RequiresPermissions("resource:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.GET)
    public String showAppendChildForm(@PathVariable("parentId") Long parentId, Model model) {
        //根据父id查询父节点信息 用于回显
        Resource parent = resourceService.findOne(parentId);
        model.addAttribute("parent", parent);
        Resource child = new Resource();
        child.setParentId(parentId);
        child.setParentIds(parent.makeSelfAsParentIds());
        model.addAttribute("resource", child);
        model.addAttribute("op", "新增子节点");
        return "resource/edit";
    }

    /**
     * 添加子节点页面中的 新增子节点 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:19
     * @return
     */
    @RequiresPermissions("resource:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.POST)
    public String create(Resource resource, RedirectAttributes redirectAttributes) {
        //创建信息资源数据
        resourceService.createResource(resource);
        redirectAttributes.addFlashAttribute("msg", "新增子节点成功");
        //重定向到当前类的首页资源请求
        return "redirect:/resource";
    }

    /**
     * 修改资源 点击修改 时获取资源信息回显到资源页面
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:35
     * @return
     */
    @RequiresPermissions("resource:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        //根据资源id查询资源信息
        model.addAttribute("resource", resourceService.findOne(id));
        model.addAttribute("op", "修改");
        return "resource/edit";
    }

    /**
     * 修改资源数据 在修改资源页面 点击 修改时
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:36
     * @return
     */
    @RequiresPermissions("resource:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Resource resource, RedirectAttributes redirectAttributes) {
        //更新资源
        resourceService.updateResource(resource);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/resource";
    }

    /**
     * 根据资源id删除资源数据 以及 当前节点的子节点
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:49
     * @return
     */
    @RequiresPermissions("resource:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        //根据资源id删除资源数据
        resourceService.deleteResource(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/resource";
    }
}
