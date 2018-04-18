package com.github.zhangkaitao.shiro.chapter21.service;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserRunAsService {

    void grantRunAs(Long fromUserId, Long toUserId);

    void revokeRunAs(Long fromUserId, Long toUserId);

    boolean exists(Long fromUserId, Long toUserId);

    List<Long> findFromUserIds(Long toUserId);

    List<Long> findToUserIds(Long fromUserId);
}
