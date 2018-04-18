package com.github.zhangkaitao.shiro.chapter23.dao;

import com.github.zhangkaitao.shiro.chapter23.entity.Authorization;

import java.util.List;

/**
 * 授权 dao - 接口
 * author : sunpanhu
 * createTime : 2018/4/18 下午2:06
 */
public interface AuthorizationDao {

    Authorization createAuthorization(Authorization authorization);

    Authorization updateAuthorization(Authorization authorization);

    void deleteAuthorization(Long authorizationId);

    Authorization findOne(Long authorizationId);

    List<Authorization> findAll();

    Authorization findByAppUser(Long appId, Long userId);
}
