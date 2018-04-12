package com.github.zhangkaitao.shiro.chapter16.dao;

import com.github.zhangkaitao.shiro.chapter16.entity.Organization;

import java.util.List;

/**
 * 组织机构DAO - 接口
 */
public interface OrganizationDao {
    /**
     * 添加组织结构的子节点 新增子节点页面的 点击 "新增子节点(保存)" 时
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:21
     * @return
     */
    Organization createOrganization(Organization organization);
    /**
     * 修改组织结构节点名称
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:20
     * @return
     */
    Organization updateOrganization(Organization organization);
    /**
     * 删除子节点
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:33
     * @return
     */
    void deleteOrganization(Long organizationId);
    /**
     * 根据组织id查询组织数据
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:07
     * @return
     */
    Organization findOne(Long organizationId);
    /**
     * 查询所有组织机构
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:53
     * @return
     */
    List<Organization> findAll();
    /**
     * 查询所有不包含当前参数组织信息的所有组织信息
     * author : sunpanhu
     * createTime : 2018/4/12 上午11:07
     * @return
     */
    List<Organization> findAllWithExclude(Organization excludeOraganization);
    /**
     * 执行移动节点操作
     * author : sunpanhu
     * createTime : 2018/4/12 上午11:10
     * @return
     */
    void move(Organization source, Organization target);
}
