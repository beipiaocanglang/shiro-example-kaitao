package com.github.zhangkaitao.shiro.chapter16.service;

import com.github.zhangkaitao.shiro.chapter16.dao.ResourceDao;
import com.github.zhangkaitao.shiro.chapter16.entity.Resource;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 资源service - 接口实现了
 */
@Service

public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;
    /**
     * 创建资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:16
     * @param resource
     * @return
     */
    public Resource createResource(Resource resource) {
        return resourceDao.createResource(resource);
    }
    /**
     * 更新资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:16
     * @param resource
     * @return
     */
    public Resource updateResource(Resource resource) {
        return resourceDao.updateResource(resource);
    }
    /**
     * 根据资源id删除资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:16
     * @param resourceId
     * @return
     */
    public void deleteResource(Long resourceId) {
        resourceDao.deleteResource(resourceId);
    }

    /**
     * 根据资源id查找资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:16
     * @param resourceId
     * @return
     */
    public Resource findOne(Long resourceId) {
        return resourceDao.findOne(resourceId);
    }

    /**
     * 查询所有资源
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:21
     * @return
     */
    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    /**
     * 根据资源的id集合查询对应的权限
     * author : sunpanhu
     * createTime : 2018/4/11 下午4:20
     * @param resourceIds
     * @return
     */
    public Set<String> findPermissions(Set<Long> resourceIds) {
        Set<String> permissions = new HashSet<String>();
        for(Long resourceId : resourceIds) {
            //根据资源id查询对应的资源
            Resource resource = findOne(resourceId);

            if(resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    public List<Resource> findMenus(Set<String> permissions) {
        //查询所有资源
        List<Resource> allResources = findAll();

        List<Resource> menus = new ArrayList<Resource>();

        for(Resource resource : allResources) {
            //是否是根节点
            if(resource.isRootNode()) {
                continue;
            }
            //资源类型不是菜单
            if(resource.getType() != Resource.ResourceType.menu) {
                continue;
            }
            //对比用户拥有的权限和 资源对应的权限是否一样 调用下面的方法
            if(!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(resource);
        }
        return menus;
    }

    //对比用户拥有的权限和 资源对应的权限是否一样
    private boolean hasPermission(Set<String> permissions, Resource resource) {
        if(StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for(String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if(p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }
}
