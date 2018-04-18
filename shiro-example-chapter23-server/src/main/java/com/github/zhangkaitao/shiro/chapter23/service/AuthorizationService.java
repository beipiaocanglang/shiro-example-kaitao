package com.github.zhangkaitao.shiro.chapter23.service;

import com.github.zhangkaitao.shiro.chapter23.entity.Authorization;

import java.util.List;
import java.util.Set;

/**
 * 授权 service - 接口
 * author : sunpanhu
 * createTime : 2018/4/18 下午2:12
 */
public interface AuthorizationService {

    Authorization createAuthorization(Authorization authorization);

    Authorization updateAuthorization(Authorization authorization);

    void deleteAuthorization(Long authorizationId);

    Authorization findOne(Long authorizationId);

    List<Authorization> findAll();

    /**
     * 根据AppKey和用户名查找其角色
     * @param username
     * @return
     */
    Set<String> findRoles(String appKey, String username);

    /**
     * 根据AppKey和用户名查找权限字符串
     * @param username
     * @return
     */
    Set<String> findPermissions(String appKey, String username);
}
