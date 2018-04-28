package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.dao.LuoPanDao;
import com.github.zhangkaitao.shiro.chapter6.dao.LuoPanDaoImpl;
import com.github.zhangkaitao.shiro.chapter6.entity.LuoPan;

import java.util.List;

/**
 * author : sunpanhu
 * create time : 2018/4/28 下午5:34
 */
public class LuoPanServiceImpl implements LuoPanService{
    private LuoPanDao luoPanDao = new LuoPanDaoImpl();
    public List<LuoPan> findAll() {
        List<LuoPan> all = luoPanDao.findAll();
        return all;
    }
}
