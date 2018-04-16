package com.github.zhangkaitao.shiro.chapter17.web.controller;

import com.github.zhangkaitao.shiro.chapter17.entity.Client;
import com.github.zhangkaitao.shiro.chapter17.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 后端数据维护控制器 - 客户端操作
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:22
 */
@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * 通过服务端首页 点击 客户端管理 获取客户端列表
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:27
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("clientList", clientService.findAll());
        return "client/list";
    }

    /**
     * 新建客户端 跳转到新建页面
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:31
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("op", "新增");
        return "client/edit";
    }

    /**
     * 点击新增客户端页面的 新增 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:31
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Client client, RedirectAttributes redirectAttributes) {
        clientService.createClient(client);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/client";
    }

    /**
     * 修改客户端 迪娜几客户端列表后面的 修改 跳转到修改页面 同时获取客户端信息
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:32
     * @return
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientService.findOne(id));
        model.addAttribute("op", "修改");
        return "client/edit";
    }

    /**
     * 提交修改 点击修改客户端页面的 修改 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:32
     * @return
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Client client, RedirectAttributes redirectAttributes) {
        clientService.updateClient(client);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/client";
    }

    /**
     * 删除客户端 点击客户端列表后面 删除 按钮 跳转到删除页面 同时获取单条客户端数据
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:33
     * @return
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientService.findOne(id));
        model.addAttribute("op", "删除");
        return "client/edit";
    }

    /**
     * 提交删除 点击删除页面的 删除 按钮
     * author : sunpanhu
     * createTime : 2018/4/16 下午5:34
     * @return
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        clientService.deleteClient(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/client";
    }
}
