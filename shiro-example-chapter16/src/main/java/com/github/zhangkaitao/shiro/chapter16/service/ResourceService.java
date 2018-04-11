package com.github.zhangkaitao.shiro.chapter16.service;

import com.github.zhangkaitao.shiro.chapter16.entity.Resource;

import java.util.List;
import java.util.Set;

/**
 * 资源service - 接口
 */
public interface ResourceService {
    /**
     * 创建资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:16
     * @param resource
     * @return
     */
    Resource createResource(Resource resource);
    /**
     * 更新资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:16
     * @param resource
     * @return
     */
    Resource updateResource(Resource resource);
    /**
     * 根据资源id删除资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:16
     * @param resourceId
     * @return
     */
    void deleteResource(Long resourceId);
    /**
     * 根据资源id查找资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:16
     * @param resourceId
     * @return
     */
    Resource findOne(Long resourceId);
    /**
     * 查询所有资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:17
     * @param null
     * @return
     */
    List<Resource> findAll();

    /**
     * 得到资源对应的权限字符串
     * @param resourceIds
     * @return
     */
    Set<String> findPermissions(Set<Long> resourceIds);

    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    List<Resource> findMenus(Set<String> permissions);
}
