package com.github.zhangkaitao.shiro.chapter6.entity;

import java.io.Serializable;

/**
 * author : sunpanhu
 * create time : 2018/4/28 下午5:19
 */
public class LuoPan{

    private String id;
    private String parentId;
    private String name;
    private Integer value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LuoPan{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
