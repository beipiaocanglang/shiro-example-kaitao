package com.github.zhangkaitao.shiro.chapter16.dao;

import com.github.zhangkaitao.shiro.chapter16.entity.Role;
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
 * 角色DAO - 接口实现类
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 创建角色
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:53
     * @return
     */
    public Role createRole(final Role role) {
        final String sql = "insert into sys_role(role, description, resource_ids, available) values(?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, role.getRole());
                psst.setString(count++, role.getDescription());
                psst.setString(count++, role.getResourceIdsStr());
                psst.setBoolean(count++, role.getAvailable());
                return psst;
            }
        }, keyHolder);
        role.setId(keyHolder.getKey().longValue());
        return role;
    }

    /**
     * 修改角色
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:53
     * @return
     */
    public Role updateRole(Role role) {
        final String sql = "update sys_role set role=?, description=?, resource_ids=?, available=? where id=?";
        jdbcTemplate.update(
                sql,
                role.getRole(), role.getDescription(), role.getResourceIdsStr(), role.getAvailable(), role.getId());
        return role;
    }

    /**
     * 根据角色id删除角色信息
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:53
     * @return
     */
    public void deleteRole(Long roleId) {
        final String sql = "delete from sys_role where id=?";
        jdbcTemplate.update(sql, roleId);
    }

    /**
     * 根据角色id查询角色信息
     * author : sunpanhu
     * createTime : 2018/4/11 下午5:00
     * @return
     */
    public Role findOne(Long roleId) {
        final String sql = "select id, role, description, resource_ids as resourceIdsStr, available from sys_role where id=?";
        List<Role> roleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Role.class), roleId);
        if(roleList.size() == 0) {
            return null;
        }
        return roleList.get(0);
    }

    /**
     * 查询所有角色
     * author : sunpanhu
     * createTime : 2018/4/16 上午10:53
     * @return
     */
    public List<Role> findAll() {
        final String sql = "select id, role, description, resource_ids as resourceIdsStr, available from sys_role";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Role.class));
    }
}
