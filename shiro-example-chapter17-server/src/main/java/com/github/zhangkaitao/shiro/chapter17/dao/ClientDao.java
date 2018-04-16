package com.github.zhangkaitao.shiro.chapter17.dao;

import com.github.zhangkaitao.shiro.chapter17.entity.Client;

import java.util.List;

/**
 * 客户端 DAO 接口
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:11
 */
public interface ClientDao {
    Client createClient(Client client);

    Client updateClient(Client client);

    void deleteClient(Long clientId);

    Client findOne(Long clientId);

    List<Client> findAll();

    Client findByClientId(String clientId);

    Client findByClientSecret(String clientSecret);
}
