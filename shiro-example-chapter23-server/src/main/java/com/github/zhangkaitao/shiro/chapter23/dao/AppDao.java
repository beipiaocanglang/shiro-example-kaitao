package com.github.zhangkaitao.shiro.chapter23.dao;

import com.github.zhangkaitao.shiro.chapter23.entity.App;

import java.util.List;

/**
 * 集中权限应用 dao - 接口
 * author : sunpanhu
 * createTime : 2018/4/18 下午2:06
 */
public interface AppDao {

    App createApp(App app);

    App updateApp(App app);

    void deleteApp(Long appId);

    App findOne(Long appId);

    List<App> findAll();

    Long findAppIdByAppKey(String appKey);
}
