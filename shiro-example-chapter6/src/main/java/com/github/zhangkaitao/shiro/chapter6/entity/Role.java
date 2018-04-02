package com.github.zhangkaitao.shiro.chapter6.entity;

import java.io.Serializable;

/**
 * 角色pojo
 */
public class Role implements Serializable {
    /**
     * 角色ID
     */
    private Long id;
    /**
     * 角色标识 程序中判断使用,如"admin"
     * 角色标识符用于在程序中进行隐式角色判断的
     */
    private String role;
    /**
     * 角色描述,UI界面显示使用
     * 描述用于以后再前台界面显示的
     */
    private String description;
    /**
     * 是否可用,如果不可用将不会添加给用户
     * 是否可用表示角色当前是否激活
     */
    private Boolean available = Boolean.FALSE;

    public Role() {}
    public Role(String role, String description, Boolean available) {
        this.role = role;
        this.description = description;
        this.available = available;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null) {
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
        return "Role{" + "id=" + id + ", role='" + role + '\'' +
                ", description='" + description + '\'' + ", available=" + available + '}';
    }
}
