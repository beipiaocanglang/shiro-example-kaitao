package com.github.zhangkaitao.shiro.chapter23.remote;

import org.apache.shiro.session.Session;

import java.io.Serializable;

/**
 * 提供了其他模块共有的依赖，如远程调用接口
 * 提供了会话的CRUD，
 * 及根据应用key和用户名获取权限上下文（包括角色和权限字符串）；
 * shiro-example-chapter23-server模块服务端实现；
 * shiro-example-chapter23-client模块客户端调用。
 *
 * author : sunpanhu
 * createTime : 2018/4/18 下午1:28
 */
public interface RemoteServiceInterface {
    Session getSession(String appKey, Serializable sessionId);

    Serializable createSession(Session session);

    void updateSession(String appKey, Session session);

    void deleteSession(String appKey, Session session);

    PermissionContext getPermissions(String appKey, String username);
}
