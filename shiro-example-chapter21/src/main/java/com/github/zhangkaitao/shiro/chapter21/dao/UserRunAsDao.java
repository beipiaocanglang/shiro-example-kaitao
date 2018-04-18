package com.github.zhangkaitao.shiro.chapter21.dao;

import java.util.List;

/**
 * 身份授权及切换身份DAO - 接口
 * author : sunpanhu
 * createTime : 2018/4/18 上午10:07
 */
public interface UserRunAsDao {

    void grantRunAs(Long fromUserId, Long toUserId);

    void revokeRunAs(Long fromUserId, Long toUserId);

    boolean exists(Long fromUserId, Long toUserId);

    List<Long> findFromUserIds(Long toUserId);

    List<Long> findToUserIds(Long fromUserId);
}
