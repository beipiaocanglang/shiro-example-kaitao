package com.github.zhangkaitao.shiro.chapter17.service;

import com.github.zhangkaitao.shiro.chapter17.dao.ClientDao;
import com.github.zhangkaitao.shiro.chapter17.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 客户端 SERVICE 接口实现类
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:14
 */
@Transactional
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDao clientDao;
    
    public Client createClient(Client client) {
        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID().toString());
        return clientDao.createClient(client);
    }
    
    public Client updateClient(Client client) {
        Client updateClient = clientDao.updateClient(client);
        return updateClient;
    }

    public void deleteClient(Long clientId) {
        clientDao.deleteClient(clientId);
    }
    
    public Client findOne(Long clientId) {
        Client client = clientDao.findOne(clientId);
        return client;
    }

    public List<Client> findAll() {
        List<Client> clientList = clientDao.findAll();
        return clientList;
    }
    
    public Client findByClientId(String clientId) {
        Client client = clientDao.findByClientId(clientId);
        return client;
    }
    
    public Client findByClientSecret(String clientSecret) {
        Client client = clientDao.findByClientSecret(clientSecret);
        return client;
    }
}
