package com.github.zhangkaitao.shiro.chapter16.dao;

import com.github.zhangkaitao.shiro.chapter16.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 组织机构DAO - 接口实现类
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 添加组织结构的子节点 新增子节点页面的 点击 "新增子节点(保存)" 时
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:21
     * @return
     */
    public Organization createOrganization(final Organization organization) {
        final String sql = "insert into sys_organization( name, parent_id, parent_ids, available) values(?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, organization.getName());
                psst.setLong(count++, organization.getParentId());
                psst.setString(count++, organization.getParentIds());
                psst.setBoolean(count++, organization.getAvailable());
                return psst;
            }
        }, keyHolder);
        organization.setId(keyHolder.getKey().longValue());
        return organization;
    }

    /**
     * 修改组织结构节点名称
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:20
     * @return
     */
    public Organization updateOrganization(Organization organization) {
        final String sql = "update sys_organization set name=?, parent_id=?, parent_ids=?, available=? where id=?";
        jdbcTemplate.update(
                sql,
                organization.getName(), organization.getParentId(), organization.getParentIds(), organization.getAvailable(), organization.getId());
        return organization;
    }
    /**
     * 删除子节点
     * author : sunpanhu
     * createTime : 2018/4/12 上午10:33
     * @return
     */
    public void deleteOrganization(Long organizationId) {
        //根据id查询节点信息
        Organization organization = findOne(organizationId);
        final String deleteSelfSql = "delete from sys_organization where id=?";
        jdbcTemplate.update(deleteSelfSql, organizationId);
        //以下删除操作 是 如果要上的节点有子节点就将当前节点和子节点一起删除
        final String deleteDescendantsSql = "delete from sys_organization where parent_ids like ?";
        jdbcTemplate.update(deleteDescendantsSql, organization.makeSelfAsParentIds() + "%");
    }

    /**
     * 根据组织id查询组织数据
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:07
     * @return
     */
    public Organization findOne(Long organizationId) {
        final String sql = "select id, name, parent_id, parent_ids, available from sys_organization where id=?";
        List<Organization> organizationList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Organization.class), organizationId);
        if(organizationList.size() == 0) {
            return null;
        }
        return organizationList.get(0);
    }

    /**
     * 查询所有组织机构
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:53
     * @return
     */
    public List<Organization> findAll() {
        final String sql = "select id, name, parent_id, parent_ids, available from sys_organization";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Organization.class));
    }
    /**
     * 查询所有不包含当前参数组织信息的所有组织信息
     * author : sunpanhu
     * createTime : 2018/4/12 上午11:07
     * @return
     */
    public List<Organization> findAllWithExclude(Organization excludeOraganization) {
        //TODO 改成not exists 利用索引
        final String sql = "select id, name, parent_id, parent_ids, available from sys_organization where id!=? and parent_ids not like ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Organization.class), excludeOraganization.getId(), excludeOraganization.makeSelfAsParentIds() + "%");
    }
    /**
     * 执行移动节点操作
     * author : sunpanhu
     * createTime : 2018/4/12 上午11:10
     * @return
     */
    public void move(Organization source, Organization target) {
        String moveSourceSql = "update sys_organization set parent_id=?,parent_ids=? where id=?";
        jdbcTemplate.update(moveSourceSql, target.getId(), target.getParentIds(), source.getId());
        String moveSourceDescendantsSql = "update sys_organization set parent_ids=concat(?, substring(parent_ids, length(?))) where parent_ids like ?";
        jdbcTemplate.update(moveSourceDescendantsSql, target.makeSelfAsParentIds(), source.makeSelfAsParentIds(), source.makeSelfAsParentIds() + "%");
    }
}
