package com.github.zhangkaitao.shiro.chapter21.service;

import com.github.zhangkaitao.shiro.chapter21.dao.UserRunAsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 身份授权及切换身份SERVICE - 接口实现类
 * author : sunpanhu
 * createTime : 2018/4/18 上午10:07
 */
@Service
public class UserRunAsServiceImpl implements UserRunAsService {
    @Autowired
    private UserRunAsDao userRunAsDao;

    /**
     * 授予身份
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:16
     */
    @Override
    public void grantRunAs(Long fromUserId, Long toUserId) {
        userRunAsDao.grantRunAs(fromUserId, toUserId);
    }
    /**
     * 回收身份
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:16
     */
    @Override
    public void revokeRunAs(Long fromUserId, Long toUserId) {
        userRunAsDao.revokeRunAs(fromUserId, toUserId);
    }
    /**
     * 关系存在判断
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:16
     * @return
     */
    @Override
    public boolean exists(Long fromUserId, Long toUserId) {
        return userRunAsDao.exists(fromUserId, toUserId);
    }

    /**
     * 查找API
     * author : sunpanhu
     * createTime : 2018/4/18 上午10:16
     * @return
     */
    @Override
    public List<Long> findFromUserIds(Long toUserId) {
        return userRunAsDao.findFromUserIds(toUserId);
    }
    @Override
    public List<Long> findToUserIds(Long fromUserId) {
        return userRunAsDao.findToUserIds(fromUserId);
    }
}
