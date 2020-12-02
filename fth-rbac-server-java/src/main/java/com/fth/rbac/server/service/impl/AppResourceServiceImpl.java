package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.controller.vo.ResourceTreeReq;
import com.fth.rbac.server.controller.vo.ResourceTreeResp;
import com.fth.rbac.server.controller.vo.ResourceSaveReq;
import com.fth.rbac.server.controller.vo.ResourceUpdateReq;
import com.fth.rbac.server.core.entity.FrAppResource;
import com.fth.rbac.server.core.entity.FrAppResourceExample;
import com.fth.rbac.server.core.enums.AppResourceStatusEnum;
import com.fth.rbac.server.core.enums.AppResourceTypeEnum;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.mapper.FrAppResourceMapper;
import com.fth.rbac.server.core.mapper.ext.FrAppResourceMapperExt;
import com.fth.rbac.server.service.AppResourceService;
import com.fth.rbac.server.service.AppRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2020/12/1 10:26 下午.
 *
 * @author fengtianhe
 */
@Service
public class AppResourceServiceImpl implements AppResourceService {
    @Autowired
    private FrAppResourceMapper appResourceMapper;
    @Autowired
    private FrAppResourceMapperExt appResourceMapperExt;
    @Autowired
    private AppRoleService appRoleService;

    @Override
    public String create(Integer userId, ResourceSaveReq resourceSaveReq) {
        FrAppResource resource = new FrAppResource();
        String appId = resourceSaveReq.getAppId();
        String resourceId = this.getResourceId(appId, resourceSaveReq.getType());

        resource.setAppId(appId);
        resource.setCreatedAt(new Date());
        resource.setCreator(userId);
        resource.setId(resourceId);
        resource.setInMenu(resourceSaveReq.getInMenu());
        resource.setPageUrl(resourceSaveReq.getPageUrl());
        resource.setResourceName(resourceSaveReq.getResourceName());
        resource.setType(resourceSaveReq.getType());
        resource.setParentId(resourceSaveReq.getParentId());
        resource.setSort(this.getResourceSort(appId, resourceSaveReq.getType(), resourceSaveReq.getParentId()));

        appResourceMapper.insertSelective(resource);
        // 给默认的超级管理员加上资源权限
        appRoleService.assignDefaultResource(appId, resourceId);

        return resourceId;
    }

    @Override
    public List<ResourceTreeResp> treeAll(ResourceTreeReq resourceTreeReq) {
        FrAppResourceExample example = new FrAppResourceExample();
        FrAppResourceExample.Criteria criteria = example.createCriteria();
        criteria.andAppIdEqualTo(resourceTreeReq.getAppId());
        if (resourceTreeReq.getType() != null) {
            criteria.andTypeEqualTo(resourceTreeReq.getType());
        }
        // 按sort正排
        example.setOrderByClause("sort");
        List<FrAppResource> frAppResources = appResourceMapper.selectByExample(example);
        return this.covertToTree(frAppResources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSort(String resourceId, Integer sort) {
        if (StringUtils.isEmpty(resourceId) || sort == null) {
            throw new CommonException(ExceptionCodes.RESOURCE_UNKNOWN_PARAMS);
        }

        FrAppResource oldResource = appResourceMapper.selectByPrimaryKey(resourceId);
        if (oldResource == null) {
            throw new CommonException(ExceptionCodes.RESOURCE_UNKNOWN);
        }
        String appId = oldResource.getAppId();
        int oldSort = oldResource.getSort();
        // 资源位置没有变动
        if (oldSort == sort) {
            throw new CommonException(ExceptionCodes.RESOURCE_SORT_NOT_CHANGE);
        }

        // 先更新当前资源的位置信息
        FrAppResource updateData = new FrAppResource();
        updateData.setId(resourceId);
        updateData.setSort(sort);
        appResourceMapper.updateByPrimaryKeySelective(updateData);
        // 更新波及到的同级资源
        int minSort = 0;
        int maxSort = 0;
        int change = +1;
        if (sort > oldSort) {
            minSort = oldSort;
            maxSort = sort;
            change = -1;
        } else {
            minSort = sort;
            maxSort = oldSort;
        }
        appResourceMapperExt.updateSiblingSort(appId, resourceId, oldResource.getParentId(), minSort, maxSort, change);
    }

    @Override
    public void updateStatus(String resourceId, Integer status) {
        if (AppResourceStatusEnum.SYMBOL_ENABLED.equals(status)) {
            this.enableResource(resourceId);
        } else if (AppResourceStatusEnum.SYMBOL_DISABLED.equals(status)) {
            this.disabledResource(resourceId);
        }
    }

    @Override
    public void updateInmenu(String resourceId, Integer inMenu) {
        FrAppResource updateData = new FrAppResource();
        updateData.setId(resourceId);
        updateData.setInMenu(inMenu);
        appResourceMapper.updateByPrimaryKeySelective(updateData);
    }

    @Override
    public FrAppResource selectById(String resourceId) {
        return appResourceMapper.selectByPrimaryKey(resourceId);
    }

    @Override
    public void update(ResourceUpdateReq resourceUpdateReq) {
        FrAppResource resource = new FrAppResource();

        resource.setId(resourceUpdateReq.getId());
        resource.setInMenu(resourceUpdateReq.getInMenu());
        resource.setPageUrl(resourceUpdateReq.getPageUrl());
        resource.setResourceName(resourceUpdateReq.getName());
        resource.setParentId(resourceUpdateReq.getParentId());

        appResourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    public void deleteById(String resourceId) {
        FrAppResource resource = appResourceMapper.selectByPrimaryKey(resourceId);
        List<FrAppResource> subList = new ArrayList<>();
        subList.add(resource);
        subList = this.querySubList(subList);

        for (FrAppResource frAppResource : subList) {
            String resId = frAppResource.getId();
            appResourceMapper.deleteByPrimaryKey(resId);
            appRoleService.deleteByResourceId(resId);
        }
    }

    /**
     * 停用资源，停用所有子资源
     *
     * @param resourceId
     */
    private void disabledResource(String resourceId) {
        FrAppResource resource = appResourceMapper.selectByPrimaryKey(resourceId);
        List<FrAppResource> subList = new ArrayList<>();
        subList.add(resource);
        subList = this.querySubList(subList);

        FrAppResource updateData;
        for (FrAppResource frAppResource : subList) {
            updateData = new FrAppResource();
            updateData.setId(frAppResource.getId());
            updateData.setStatus(AppResourceStatusEnum.SYMBOL_DISABLED);
            appResourceMapper.updateByPrimaryKeySelective(updateData);
        }
    }

    /**
     * 查询子资源
     *
     * @param resources
     * @return
     */
    private List<FrAppResource> querySubList(List<FrAppResource> resources) {
        FrAppResourceExample example = new FrAppResourceExample();
        FrAppResourceExample.Criteria criteria = example.createCriteria();
        List<FrAppResource> result = new ArrayList<>();
        if (resources != null && resources.size() > 0) {
            result.addAll(resources);
            for (FrAppResource tagMenu : resources) {
                if (tagMenu.getType().equals(AppResourceTypeEnum.SYMBOL_PAGE)) {
                    criteria.andParentIdEqualTo(tagMenu.getId());
                    List<FrAppResource> list = appResourceMapper.selectByExample(example);
                    if (list != null && list.size() > 0) {
                        result.addAll(querySubList(list));
                    }
                }
            }
        }
        return result;
    }

    /**
     * 启用资源，启用所有父资源
     *
     * @param resourceId
     */
    private void enableResource(String resourceId) {
        FrAppResource resource = appResourceMapper.selectByPrimaryKey(resourceId);
        // 如果不是顶级资源，就一直往上找, 依次启用
        FrAppResource updateData = new FrAppResource();
        updateData.setId(resource.getId());
        updateData.setStatus(AppResourceStatusEnum.SYMBOL_ENABLED);
        appResourceMapper.updateByPrimaryKeySelective(updateData);

        if (!"0".equals(resource.getParentId())) {
            this.enableResource(resource.getParentId());
        }
    }

    /**
     * 利用红黑树将列表转换树结构
     *
     * @param resources
     * @return
     */
    public List<ResourceTreeResp> covertToTree(List<FrAppResource> resources) {
        List<ResourceTreeResp> resourceTreeVo = ResourceTreeResp.covert(resources);

        // 按sort顺序转成map
        Map<String, ResourceTreeResp> resourceMap = resourceTreeVo.stream()
                .collect(Collectors.toMap(ResourceTreeResp::getId, resource -> resource));
        List<ResourceTreeResp> resourceTree = new ArrayList<>();
        for (ResourceTreeResp menu : resourceTreeVo) {
            if ("0".equals(menu.getParentId())) {
                resourceTree.add(menu);
                continue;
            }
            ResourceTreeResp parent = resourceMap.get(menu.getParentId());
            if (parent != null) {
                if (CollectionUtils.isEmpty(parent.getChildren())) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(menu);
            }
        }
        return resourceTree;
    }

    public String getResourceId(String appId, Integer type) {
        long count = this.count(appId, type);
        String resourceId = String.format("%04d", count);
        String typeName = type.equals(AppResourceTypeEnum.SYMBOL_PAGE) ? "PAGE" : "BTN";
        return "RES_" + typeName + "_" + appId + "_" + resourceId;
    }

    public long count(String appId, Integer type) {
        FrAppResourceExample example = new FrAppResourceExample();
        example.createCriteria()
                .andAppIdEqualTo(appId)
                .andTypeEqualTo(type);
        long l = appResourceMapper.countByExample(example);
        return l;
    }

    private Integer getResourceSort(String appId, Integer type, String parentId) {
        FrAppResourceExample example = new FrAppResourceExample();
        example.createCriteria()
                .andAppIdEqualTo(appId)
                .andTypeEqualTo(type)
                .andParentIdEqualTo(parentId);
        List<FrAppResource> frAppResources = appResourceMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(frAppResources)) {
            return 0;
        }
        FrAppResource resource = frAppResources.stream()
                .max(Comparator.comparing(FrAppResource::getSort))
                .get();
        return resource.getSort() + 1;
    }
}
