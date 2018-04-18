package com.github.zhangkaitao.shiro.chapter23.remote;

import com.github.zhangkaitao.shiro.chapter23.service.AuthorizationService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Set;

/**
 * 远程调用服务
 * 将会使用HTTP调用器暴露为远程服务，
 * 这样其他应用就可以使用相应的客户端调用这些接口进行Session的集中维护及根据AppKey和用户名获取角色/权限字符串集合。
 * 此处没有实现安全校验功能，如果是局域网内使用可以通过限定IP完成；
 * 否则需要使用如《第二十章 无状态Web应用集成》中的技术完成安全校验
 * author : sunpanhu
 * createTime : 2018/4/18 下午2:10
 */
public class RemoteService implements RemoteServiceInterface {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public Session getSession(String appKey, Serializable sessionId) {
        return sessionDAO.readSession(sessionId);
    }

    @Override
    public Serializable createSession(Session session) {
        return sessionDAO.create(session);
    }

    @Override
    public void updateSession(String appKey, Session session) {
        sessionDAO.update(session);
    }

    @Override
    public void deleteSession(String appKey, Session session) {
        sessionDAO.delete(session);
    }

    @Override
    public PermissionContext getPermissions(String appKey, String username) {
        Set<String> roles = authorizationService.findRoles(appKey, username);
        Set<String> permissions = authorizationService.findPermissions(appKey, username);

        PermissionContext permissionContext = new PermissionContext();
        permissionContext.setRoles(roles);
        permissionContext.setPermissions(permissions);

        return permissionContext;
    }
}
