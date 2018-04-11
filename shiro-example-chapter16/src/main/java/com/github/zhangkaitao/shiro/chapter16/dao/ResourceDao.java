package com.github.zhangkaitao.shiro.chapter16.dao;

import com.github.zhangkaitao.shiro.chapter16.entity.Resource;

import java.util.List;

/**
 * 资源DAO - 接口
 */
public interface ResourceDao {
    /**
     * 创建资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    Resource createResource(Resource resource);
    /**
     * 根据资源id更新资源信息
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    Resource updateResource(Resource resource);
    /**
     * 根据资源id删除资源数据
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    void deleteResource(Long resourceId);
    /**
     * 根据资源id查询资源数据
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    Resource findOne(Long resourceId);
    /**
     * 查询所有资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    List<Resource> findAll();
}
