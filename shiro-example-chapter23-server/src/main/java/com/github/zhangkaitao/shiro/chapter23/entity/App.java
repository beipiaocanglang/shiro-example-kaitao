package com.github.zhangkaitao.shiro.chapter23.entity;

import java.io.Serializable;

/**
 * 集中权限应用 pojo
 * 所有集中权限的应用；在此处需要指定应用key(app_key)和应用安全码（app_secret），app在访问server时需要指定自己的app_key和用户名来获取该app对应用户权限信息；
 * 另外app_secret可以认为app的密码，比如需要安全访问时可以考虑使用它，可参考《第二十章 无状态Web应用集成》。
 * 另外available属性表示该应用当前是否开启；
 * 如果false表示该应用当前不可用，即不能获取到相应的权限信息。
 * author : sunpanhu
 * createTime : 2018/4/18 下午2:04
 */
public class App implements Serializable {
    private Long id;
    private String name;
    private String appKey;
    private String appSecret;
    private Boolean available = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        App app = (App) o;

        if (id != null ? !id.equals(app.id) : app.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", appKey='" + appKey + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", available=" + available +
                '}';
    }
}
