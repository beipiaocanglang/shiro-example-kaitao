package com.github.zhangkaitao.shiro.chapter16.web.taglib;

import com.github.zhangkaitao.shiro.chapter16.entity.Organization;
import com.github.zhangkaitao.shiro.chapter16.entity.Resource;
import com.github.zhangkaitao.shiro.chapter16.entity.Role;
import com.github.zhangkaitao.shiro.chapter16.service.OrganizationService;
import com.github.zhangkaitao.shiro.chapter16.service.ResourceService;
import com.github.zhangkaitao.shiro.chapter16.service.RoleService;
import com.github.zhangkaitao.shiro.spring.SpringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Web层标签库
 * 提供了函数标签实现，有根据编号显示资源/角色/组织机构名称，其定义放在src/main/webapp/tld/zhang-functions.tld。
 */
public class Functions {

    /**
     * obj in collection
     * 和src/main/webapp/tld/zhang-functions.tld文件内容对应
     * @param iterable
     * @param element
     * @return
     * author : sunpanhu
     * createTime : 2018/4/9 上午10:08
     */
    public static boolean in(Iterable iterable, Object element) {
        if(iterable == null) {
            return false;
        }
        return CollectionUtils.contains(iterable.iterator(), element);
    }

    /**
     * 根据id显示组织机构名称
     * 和src/main/webapp/tld/zhang-functions.tld文件内容对应
     * @param organizationId
     * @return
     * author : sunpanhu
     * createTime : 2018/4/9 上午10:08
     */
    public static String organizationName(Long organizationId) {
        Organization organization = getOrganizationService().findOne(organizationId);
        if(organization == null) {
            return "";
        }
        return organization.getName();
    }

    /**
     * 根据id列表显示多个组织机构名称
     * 和src/main/webapp/tld/zhang-functions.tld文件内容对应
     * @param organizationIds
     * @return
     * author : sunpanhu
     * createTime : 2018/4/9 上午10:08
     */
    public static String organizationNames(Collection<Long> organizationIds) {
        if(CollectionUtils.isEmpty(organizationIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for(Long organizationId : organizationIds) {
            Organization organization = getOrganizationService().findOne(organizationId);
            if(organization == null) {
                return "";
            }
            s.append(organization.getName());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    /**
     * 根据id显示角色名称
     * 和src/main/webapp/tld/zhang-functions.tld文件内容对应
     * @param roleId
     * @return
     * author : sunpanhu
     * createTime : 2018/4/9 上午10:08
     */
    public static String roleName(Long roleId) {
        Role role = getRoleService().findOne(roleId);
        if(role == null) {
            return "";
        }
        return role.getDescription();
    }

    /**
     * 根据id列表显示多个角色名称
     * 和src/main/webapp/tld/zhang-functions.tld文件内容对应
     * @param roleIds
     * @return
     * author : sunpanhu
     * createTime : 2018/4/9 上午10:08
     */
    public static String roleNames(Collection<Long> roleIds) {
        if(CollectionUtils.isEmpty(roleIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for(Long roleId : roleIds) {
            Role role = getRoleService().findOne(roleId);
            if(role == null) {
                return "";
            }
            s.append(role.getDescription());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    /**
     * 根据id显示资源名称
     * 和src/main/webapp/tld/zhang-functions.tld文件内容对应
     * @param resourceId
     * @return
     * author : sunpanhu
     * createTime : 2018/4/9 上午10:08
     */
    public static String resourceName(Long resourceId) {
        Resource resource = getResourceService().findOne(resourceId);
        if(resource == null) {
            return "";
        }
        return resource.getName();
    }

    /**
     * 根据id列表显示多个资源名称
     * 和src/main/webapp/tld/zhang-functions.tld文件内容对应
     * @param resourceIds
     * @return
     * author : sunpanhu
     * createTime : 2018/4/9 上午10:08
     */
    public static String resourceNames(Collection<Long> resourceIds) {
        if(CollectionUtils.isEmpty(resourceIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for(Long resourceId : resourceIds) {
            Resource resource = getResourceService().findOne(resourceId);
            if(resource == null) {
                return "";
            }
            s.append(resource.getName());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    private static OrganizationService organizationService;
    private static RoleService roleService;
    private static ResourceService resourceService;

    //上面调用
    public static OrganizationService getOrganizationService() {
        if(organizationService == null) {
            organizationService = SpringUtils.getBean(OrganizationService.class);
        }
        return organizationService;
    }

    //上面调用
    public static RoleService getRoleService() {
        if(roleService == null) {
            roleService = SpringUtils.getBean(RoleService.class);
        }
        return roleService;
    }

    //上面调用
    public static ResourceService getResourceService() {
        if(resourceService == null) {
            resourceService = SpringUtils.getBean(ResourceService.class);
        }
        return resourceService;
    }
}

