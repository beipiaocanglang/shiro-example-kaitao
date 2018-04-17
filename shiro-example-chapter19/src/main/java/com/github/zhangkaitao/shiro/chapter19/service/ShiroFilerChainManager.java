package com.github.zhangkaitao.shiro.chapter19.service;

import com.github.zhangkaitao.shiro.chapter19.entity.UrlFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-25
 * <p>Version: 1.0
 */
@Service
public class ShiroFilerChainManager {

    @Autowired
    private DefaultFilterChainManager filterChainManager;

    private Map<String, NamedFilterList> defaultFilterChains;

    /**
     * Spring容器启动时会调用init方法把在spring配置文件中配置的默认拦截器保存下来，之后会自动与数据库中的配置进行合并
     * author : sunpanhu
     * createTime : 2018/4/17 下午3:01
     */
    @PostConstruct
    public void init() {
        defaultFilterChains = new HashMap<String, NamedFilterList>(filterChainManager.getFilterChains());
    }

    /**
     * UrlFilterServiceImpl会在Spring容器启动或进行增删改UrlFilter时进行注册URL拦截器到Shiro。
     * author : sunpanhu
     * createTime : 2018/4/17 下午3:01
     */
    public void initFilterChains(List<UrlFilter> urlFilters) {
        //1、首先删除以前老的filter chain并注册默认的
        filterChainManager.getFilterChains().clear();
        if(defaultFilterChains != null) {
            filterChainManager.getFilterChains().putAll(defaultFilterChains);
        }

        //2、循环URL Filter 注册filter chain
        for (UrlFilter urlFilter : urlFilters) {
            String url = urlFilter.getUrl();
            //注册roles filter
            if (!StringUtils.isEmpty(urlFilter.getRoles())) {
                filterChainManager.addToChain(url, "roles", urlFilter.getRoles());
            }
            //注册perms filter
            if (!StringUtils.isEmpty(urlFilter.getPermissions())) {
                filterChainManager.addToChain(url, "perms", urlFilter.getPermissions());
            }
        }
    }
}
