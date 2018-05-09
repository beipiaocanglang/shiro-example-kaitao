package com.github.zhangkaitao.shiro.chapter19.service;

import com.github.zhangkaitao.shiro.chapter19.entity.UrlFilter;

import java.util.List;

/**
 * 动态URL权限控制service - 接口
 * 基本的URL拦截的增删改查实现。
 * author : sunpanhu
 * createTime : 2018/4/17 下午2:01
 */
public interface UrlFilterService {

    UrlFilter createUrlFilter(UrlFilter urlFilter);

    UrlFilter updateUrlFilter(UrlFilter urlFilter);

    void deleteUrlFilter(Long urlFilterId);

    UrlFilter findOne(Long urlFilterId);

    List<UrlFilter> findAll();
}
