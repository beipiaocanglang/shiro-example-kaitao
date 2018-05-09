package com.github.zhangkaitao.shiro.spring;

import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器
 *
 * 默认情况下如使用ShiroFilterFactoryBean创建shiroFilter时，默认使用PathMatchingFilterChainResolver进行解析，
 * 而它默认是根据当前请求的URL获取相应的拦截器链，使用Ant模式进行URL匹配；
 * 默认使用DefaultFilterChainManager进行拦截器链的管理。
 *
 * 默认实现有点小问题：
 *      如果多个拦截器链都匹配了当前请求URL，那么只返回第一个找到的拦截器链；
 *      默认的PathMatchingFilterChainResolver和DefaultFilterChainManager不能满足我们的需求，下面时扩展的代码
 * author : sunpanhu
 * createTime : 2018/4/17 下午3:06
 */
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {

    //同一个包下的
    private CustomDefaultFilterChainManager customDefaultFilterChainManager;

    public void setCustomDefaultFilterChainManager(CustomDefaultFilterChainManager customDefaultFilterChainManager) {
        this.customDefaultFilterChainManager = customDefaultFilterChainManager;
        setFilterChainManager(customDefaultFilterChainManager);
    }

    //默认流程
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }

        String requestURI = getPathWithinApplication(request);

        List<String> chainNames = new ArrayList<String>();
        //the 'chain names' in this implementation are actually path patterns defined by the user.  We just use them
        //as the chain name for the FilterChainManager's requirements
        for (String pathPattern : filterChainManager.getChainNames()) {

            // If the path does match, then pass on to the subclass implementation for specific checks:
            if (pathMatches(pathPattern, requestURI)) {
                chainNames.add(pathPattern);
            }
        }

        if(chainNames.size() == 0) {
            return null;
        }

        //解决类注释上说的小问题
        //和默认的PathMatchingFilterChainResolver区别是，此处得到所有匹配的拦截器链，
        //然后通过调用CustomDefaultFilterChainManager.proxy(originalChain, chainNames)进行合并后代理。
        FilterChain filterChain = customDefaultFilterChainManager.proxy(originalChain, chainNames);
        return filterChain;
    }
}
