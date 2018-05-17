package com.github.zhangkaitao.shiro.chapter23.client;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;

/**
 * author : sunpanhu
 * createTime : 2018/5/17 下午1:41
 */
public class ClientShiroFilterFactoryBean extends ShiroFilterFactoryBean implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 设置拦截器，设置格式如“filterName=filterBeanName; filterName=filterBeanName”；多个之间分号分隔；
     * 然后通过applicationContext获取filterBeanName对应的Bean注册到拦截器Map中；
     * @param filters
     */
    public void setFiltersStr(String filters) {
        if(StringUtils.isEmpty(filters)) {
            return;
        }
        String[] filterArray = filters.split(";");
        for(String filter : filterArray) {
            String[] o = filter.split("=");
            getFilters().put(o[0], (Filter)applicationContext.getBean(o[1]));
        }
    }

    /**
     * 设置拦截器链，设置格式如“url=filterName1[config],filterName2; url=filterName1[config],filterName2”；
     * 多个之间分号分隔
     * @param filterChainDefinitions
     */
    public void setFilterChainDefinitionsStr(String filterChainDefinitions) {
        if(StringUtils.isEmpty(filterChainDefinitions)) {
            return;
        }
        String[] chainDefinitionsArray = filterChainDefinitions.split(";");
        for(String filter : chainDefinitionsArray) {
            String[] o = filter.split("=");
            getFilterChainDefinitionMap().put(o[0], o[1]);
        }
    }
}
