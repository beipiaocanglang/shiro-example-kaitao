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

    /**
     * 添加组织结构的子节点 新增子节点页面的 点击 "新增子节点(保存)" 时
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:21
     * @return
     */
    public Organization createOrganization(Organization organization) {
        return organizationDao.createOrganization(organization);
    }

    /**
     * 修改组织结构节点名称
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:20
     * @return
     */
    public Organization updateOrganization(Organization organization) {
        return organizationDao.updateOrganization(organization);
    }
    /**
     * 删除子节点
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:33
     * @return
     */
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
    /**
     * 查询所有不包含当前参数组织信息的所有组织信息
     * author : sunpanhu
     * createTime : 2018/4/12 上午11:07
     * @return
     */
    public List<Organization> findAllWithExclude(Organization excludeOraganization) {
        return organizationDao.findAllWithExclude(excludeOraganization);
    }
    /**
     * 执行移动节点操作
     * author : sunpanhu
     * createTime : 2018/4/12 上午11:10
     * @return
     */
    public void move(Organization source, Organization target) {
        organizationDao.move(source, target);
    }
}
