package com.github.zhangkaitao.shiro.chapter4.authenticator;

import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 自定义多Realm认证 继承ModularRealmAuthenticator
 * 获取ini配置文件中的 集合注入的值
 * author : sunpanhu
 * createTime : 2018/4/23 下午1:47
 */
public class MyAuthenticator extends ModularRealmAuthenticator {

    public void setBytes(byte[] bytes) {
        System.out.println(new String(bytes));
    }

    public void setArray(int[] ints) {
        System.out.println(Arrays.toString(ints));
    }

    public void setSet(Set<Realm> realms) {
        System.out.println(realms);
    }

    public void setMap(Map<Object, Object> maps) {
        System.out.println(maps);
        System.out.println(maps.get("1"));
    }
}
