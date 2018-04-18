package com.github.zhangkaitao.shiro.chapter21.dao;

import java.util.List;

/**
 * 身份授权及切换身份DAO - 接口
 * author : sunpanhu
 * createTime : 2018/4/18 上午10:07
 */
public interface UserRunAsDao {
    /**
     * 授予身份
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:16
     */
    void grantRunAs(Long fromUserId, Long toUserId);
    /**
     * 回收身份
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:16
     */
    void revokeRunAs(Long fromUserId, Long toUserId);
    /**
     * 关系存在判断
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:16
     * @return
     */
    boolean exists(Long fromUserId, Long toUserId);

    /**
     * 查找API
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:16
     * @return
     */
    List<Long> findFromUserIds(Long toUserId);
    List<Long> findToUserIds(Long fromUserId);
}
