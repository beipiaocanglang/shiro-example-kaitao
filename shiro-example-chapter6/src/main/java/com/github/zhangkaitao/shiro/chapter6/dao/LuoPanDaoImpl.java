package com.github.zhangkaitao.shiro.chapter6.dao;

import com.github.zhangkaitao.shiro.chapter6.JdbcTemplateUtils;
import com.github.zhangkaitao.shiro.chapter6.entity.LuoPan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * author : sunpanhu
 * create time : 2018/4/28 下午5:26
 */
public class LuoPanDaoImpl implements LuoPanDao{

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.jdbcTemplate();

    public List<LuoPan> findAll() {
        String sql = "select * from u_luopan";
        List<LuoPan> luoPanList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(LuoPan.class));

        return luoPanList;
    }
}
