package com.github.zhangkaitao.shiro.chapter17.entity;

import java.io.Serializable;

/**
 * 客户端pojo
 * 存储客户端的的客户端id及客户端安全key；在进行授权时使用。
 * author : sunpanhu
 * createTime : 2018/4/16 下午2:06
 */
public class Client implements Serializable {
    //主键id
    private Long id;
    //客户端名称
    private String clientName;
    //客户端id
    private String clientId;
    //客户端安全key
    private String clientSecret;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
