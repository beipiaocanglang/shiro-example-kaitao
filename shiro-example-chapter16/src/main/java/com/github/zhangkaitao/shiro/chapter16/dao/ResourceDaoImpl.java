package com.github.zhangkaitao.shiro.chapter16.dao;

import com.github.zhangkaitao.shiro.chapter16.entity.Resource;
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
 * 资源DAO - 接口实现类
 */
@Repository
public class ResourceDaoImpl implements ResourceDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 创建资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    public Resource createResource(final Resource resource) {
        final String sql = "insert into sys_resource(name, type, url, permission, parent_id, parent_ids, available) values(?,?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, resource.getName());
                psst.setString(count++, resource.getType().name());
                psst.setString(count++, resource.getUrl());
                psst.setString(count++, resource.getPermission());
                psst.setLong(count++, resource.getParentId());
                psst.setString(count++, resource.getParentIds());
                psst.setBoolean(count++, resource.getAvailable());
                return psst;
            }
        }, keyHolder);
        resource.setId(keyHolder.getKey().longValue());
        return resource;
    }

    /**
     * 根据资源id更新资源信息
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    public Resource updateResource(Resource resource) {
        final String sql = "update sys_resource set name=?, type=?, url=?, permission=?, parent_id=?, parent_ids=?, available=? where id=?";
        jdbcTemplate.update(
                sql,
                resource.getName(), resource.getType().name(), resource.getUrl(), resource.getPermission(), resource.getParentId(), resource.getParentIds(), resource.getAvailable(), resource.getId());
        return resource;
    }

    /**
     * 根据资源id删除资源数据
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    public void deleteResource(Long resourceId) {
        Resource resource = findOne(resourceId);
        final String deleteSelfSql = "delete from sys_resource where id=?";
        jdbcTemplate.update(deleteSelfSql, resourceId);
        final String deleteDescendantsSql = "delete from sys_resource where parent_ids like ?";
        jdbcTemplate.update(deleteDescendantsSql, resource.makeSelfAsParentIds() + "%");
    }

    /**
     * 根据资源id查询资源数据
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    public Resource findOne(Long resourceId) {
        final String sql = "select id, name, type, url, permission, parent_id, parent_ids, available from sys_resource where id=?";
        List<Resource> resourceList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Resource.class), resourceId);
        if(resourceList.size() == 0) {
            return null;
        }
        return resourceList.get(0);
    }

    /**
     * 查询所有资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:10
     * @return
     */
    public List<Resource> findAll() {
        final String sql = "select id, name, type, url, permission, parent_id, parent_ids, available from sys_resource order by concat(parent_ids, id) asc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Resource.class));
    }
}
