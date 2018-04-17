package com.github.zhangkaitao.shiro.chapter19.service;

import com.github.zhangkaitao.shiro.chapter19.dao.UrlFilterDao;
import com.github.zhangkaitao.shiro.chapter19.entity.UrlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 动态URL权限控制service - 接口实现类
 * UrlFilterServiceImpl在进行新增、修改、删除时会调用initFilterChain来重新初始化Shiro的URL拦截器链，
 * 即同步数据库中的URL拦截器定义到Shiro中。
 * 此处也要注意如果直接修改数据库是不会起作用的，因为只要调用这几个Service方法时才同步。
 * 另外当容器启动时会自动回调initFilterChain来完成容器启动后的URL拦截器的注册。
 * author : sunpanhu
 * createTime : 2018/4/17 下午2:01
 */
@Service
public class UrlFilterServiceImpl implements UrlFilterService {

    @Autowired
    private UrlFilterDao urlFilterDao;

    @Autowired
    private ShiroFilerChainManager shiroFilerChainManager;

    public UrlFilter createUrlFilter(UrlFilter urlFilter) {
        urlFilterDao.createUrlFilter(urlFilter);
        initFilterChain();
        return urlFilter;
    }

    public UrlFilter updateUrlFilter(UrlFilter urlFilter) {
        urlFilterDao.updateUrlFilter(urlFilter);
        initFilterChain();
        return urlFilter;
    }

    public void deleteUrlFilter(Long urlFilterId) {
        urlFilterDao.deleteUrlFilter(urlFilterId);
        initFilterChain();
    }

    public UrlFilter findOne(Long urlFilterId) {
        return urlFilterDao.findOne(urlFilterId);
    }
    
    public List<UrlFilter> findAll() {
        return urlFilterDao.findAll();
    }

    /**
     * 增 删 改 时会调用此方法重新初始化Shiro的URL拦截器链
     * author : sunpanhu
     * createTime : 2018/4/17 下午2:38
     */
    @PostConstruct
    public void initFilterChain() {
        shiroFilerChainManager.initFilterChains(findAll());
    }
}
