package com.github.zhangkaitao.shiro.chapter16.service;

import com.github.zhangkaitao.shiro.chapter16.dao.OrganizationDao;
import com.github.zhangkaitao.shiro.chapter16.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织机构service - 接口实现类
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationDao organizationDao;

    
    public Organization createOrganization(Organization organization) {
        return organizationDao.createOrganization(organization);
    }
    
    public Organization updateOrganization(Organization organization) {
        return organizationDao.updateOrganization(organization);
    }
    
    public void deleteOrganization(Long organizationId) {
        organizationDao.deleteOrganization(organizationId);
    }

    /**
     * 根据组织id查询组织数据
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:07
     * @return
     */
    public Organization findOne(Long organizationId) {
        return organizationDao.findOne(organizationId);
    }
    /**
     * 查询所有组织机构
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:53
     * @return
     */
    public List<Organization> findAll() {
        return organizationDao.findAll();
    }
    
    public List<Organization> findAllWithExclude(Organization excludeOraganization) {
        return organizationDao.findAllWithExclude(excludeOraganization);
    }

    public void move(Organization source, Organization target) {
        organizationDao.move(source, target);
    }
}
