package com.github.zhangkaitao.shiro.chapter24.web.controller;

import com.github.zhangkaitao.shiro.chapter24.Constants;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

/**
 * 会话控制器
 * author : sunpanhu
 * createTime : 2018/4/19 下午1:32
 */
@RequiresPermissions("session:*")
@Controller
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private SessionDAO sessionDAO;
    /**
     * 提供了展示所有在线会话列表，通过sessionDAO.getActiveSessions()获取所有在线的会话。
     * 此处展示会话列表的缺点是：
     *      sessionDAO.getActiveSessions()提供了获取所有活跃会话集合，如果做一般企业级应用问题不大，因为在线用户不多；
     *      但是如果应用的在线用户非常多，此种方法就不适合了，
     * 解决方案就是分页获取：
     *      Page<Session> getActiveSessions(int pageNumber, int pageSize);
     *      Page对象除了包含pageNumber、pageSize属性之外，还包含totalSessions（总会话数）、Collection<Session> （当前页的会话）。
     * author : sunpanhu
     * createTime : 2018/4/19 下午1:32
     * @return
     */
    @RequestMapping()
    public String list(Model model) {
        Collection<Session> sessions =  sessionDAO.getActiveSessions();
        model.addAttribute("sessions", sessions);
        model.addAttribute("sessionCount", sessions.size());
        return "sessions/list";
    }

    /**
     * 强制退出某一个会话，
     * 此处只在指定会话中设置Constants.SESSION_FORCE_LOGOUT_KEY属性，
     * 之后通过ForceLogoutFilter判断并进行强制退出
     * author : sunpanhu
     * createTime : 2018/4/19 下午1:33
     * @return
     */
    @RequestMapping("/{sessionId}/forceLogout")
    public String forceLogout(
            @PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
        try {
            Session session = sessionDAO.readSession(sessionId);
            if(session != null) {
                session.setAttribute(Constants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
            }
        } catch (Exception e) {/*ignore*/}
        redirectAttributes.addFlashAttribute("msg", "强制退出成功！");
        return "redirect:/sessions";
    }
}
