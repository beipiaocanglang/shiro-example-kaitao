package com.github.zhangkaitao.shiro.chapter10;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBC模板
 */
public class JdbcTemplateUtils {

    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate jdbcTemplate() {
        if(jdbcTemplate == null) {
            //调用下面的方法创建 jdbcTemplate 模板
            jdbcTemplate = createJdbcTemplate();
        }
        return jdbcTemplate;
    }

    //创建 jdbcTemplate 模板
    private static JdbcTemplate createJdbcTemplate() {

        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/shiro");
        ds.setUsername("root");
        ds.setPassword("root");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate;
    }
}
