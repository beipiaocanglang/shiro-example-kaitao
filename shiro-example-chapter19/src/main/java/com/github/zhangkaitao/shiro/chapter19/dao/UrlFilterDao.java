package com.github.zhangkaitao.shiro.chapter19.dao;

import com.github.zhangkaitao.shiro.chapter19.entity.UrlFilter;

import java.util.List;

/**
 * 动态URL权限控制DAO - 接口
 * author : sunpanhu
 * createTime : 2018/4/17 下午2:02
 */
public interface UrlFilterDao {

    UrlFilter createUrlFilter(UrlFilter urlFilter);

    UrlFilter updateUrlFilter(UrlFilter urlFilter);

    void deleteUrlFilter(Long urlFilterId);

    UrlFilter findOne(Long urlFilterId);

    List<UrlFilter> findAll();
}
