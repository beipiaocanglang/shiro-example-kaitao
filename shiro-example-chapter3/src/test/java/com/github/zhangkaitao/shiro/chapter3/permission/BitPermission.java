package com.github.zhangkaitao.shiro.chapter3.permission;

import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.authz.Permission;

/**
 * 自定义 Permission
 * author : sunpanhu
 * createTime : 2018/4/20 下午3:52
 */
public class BitPermission implements Permission {

    private String resourceIdentify;
    private int permissionBit;
    private String instanceId;

    /**
     * BitPermission用于实现位移方式的权限
     *  如规则：
     *      +资源字符串+权限位+实例ID
     *      以+开头 中间通过+分割
     *
     *  权限：
     *     0： 表示所有权限
     *     1： 新增 0001
     *     2： 修改 0010
     *     4： 删除 0100
     *     8： 查看 1000
     *
     *  如 ：
     *      +user+10 表示对资源user拥有修改/查看权限
     *      10 = 2 + 8
     *
     *  不考虑一些异常情况
     * author : sunpanhu
     * createTime : 2018/4/23 上午10:28
     * @return
     */
    public BitPermission(String permissionString) {
        String[] array = permissionString.split("\\+");

        if(array.length > 1) {
            resourceIdentify = array[1];
        }

        if(StringUtils.isEmpty(resourceIdentify)) {
            resourceIdentify = "*";
        }

        if(array.length > 2) {
            permissionBit = Integer.valueOf(array[2]);
        }

        if(array.length > 3) {
            instanceId = array[3];
        }

        if(StringUtils.isEmpty(instanceId)) {
            instanceId = "*";
        }
    }

    //Permission接口提供了boolean implies(Permission p)方法用于判断权限匹配的；
    public boolean implies(Permission p) {
        if(!(p instanceof BitPermission)) {
            return false;
        }
        BitPermission other = (BitPermission) p;

        if(!("*".equals(this.resourceIdentify) || this.resourceIdentify.equals(other.resourceIdentify))) {
            return false;
        }

        if(!(this.permissionBit ==0 || (this.permissionBit & other.permissionBit) != 0)) {
            return false;
        }

        if(!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BitPermission{" +
                "resourceIdentify='" + resourceIdentify + '\'' +
                ", permissionBit=" + permissionBit +
                ", instanceId='" + instanceId + '\'' +
                '}';
    }
}
