package com.github.zhangkaitao.shiro.chapter17.dao;

import com.github.zhangkaitao.shiro.chapter17.entity.User;
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
 * 用户端 DAO 接口实现类
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:12
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 创建用户
     * @param user
     */
    public User createUser(final User user) {
        final String sql = "insert into oauth2_user(username, password, salt) values(?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, user.getUsername());
                psst.setString(count++, user.getPassword());
                psst.setString(count++, user.getSalt());
                return psst;
            }
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public User updateUser(User user) {
        String sql = "update oauth2_user set username=?, password=?, salt=? where id=?";
        jdbcTemplate.update(
                sql,
                user.getUsername(), user.getPassword(), user.getSalt(), user.getId());
        return user;
    }
    /**
     * 删除用户
     * @param userId
     */
    public void deleteUser(Long userId) {
        String sql = "delete from oauth2_user where id=?";
        jdbcTemplate.update(sql, userId);
    }
    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    public User findOne(Long userId) {
        String sql = "select id, username, password, salt from oauth2_user where id=?";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), userId);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }
    /**
     * 查询所有用户列表
     * @return
     */
    public List<User> findAll() {
        String sql = "select id, username, password, salt from oauth2_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
    }
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        String sql = "select id, username, password, salt from oauth2_user where username=?";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), username);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }
}
