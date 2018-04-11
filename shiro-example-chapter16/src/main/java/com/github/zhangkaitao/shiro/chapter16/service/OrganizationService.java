package com.github.zhangkaitao.shiro.chapter16.service;

import com.github.zhangkaitao.shiro.chapter16.entity.Organization;

import java.util.List;

/**
 * 组织机构service - 接口
 */
public interface OrganizationService {

    Organization createOrganization(Organization organization);

    Organization updateOrganization(Organization organization);

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

    Object findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
