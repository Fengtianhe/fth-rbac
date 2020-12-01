package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.controller.vo.SaveResourceReq;
import com.fth.rbac.server.core.entity.FrAppResource;
import com.fth.rbac.server.core.entity.FrAppResourceExample;
import com.fth.rbac.server.core.enums.AppResourceTypeEnum;
import com.fth.rbac.server.core.mapper.FrAppResourceMapper;
import com.fth.rbac.server.service.AppResourceService;
import com.fth.rbac.server.service.AppRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
    private AppRoleService appRoleService;

    @Override
    public String create(Integer userId, SaveResourceReq saveResourceReq) {
        FrAppResource resource = new FrAppResource();
        String appId = saveResourceReq.getAppId();
        String resourceId = this.getResourceId(appId, saveResourceReq.getType());

        resource.setAppId(appId);
        resource.setCreatedAt(new Date());
        resource.setCreator(userId);
        resource.setId(resourceId);
        resource.setInMenu(saveResourceReq.getInMenu());
        resource.setPageUrl(saveResourceReq.getPageUrl());
        resource.setResourceName(saveResourceReq.getResourceName());
        resource.setType(saveResourceReq.getType());
        resource.setParentId(saveResourceReq.getParentId());
        resource.setSort(this.getResourceSort(appId, saveResourceReq.getType(), saveResourceReq.getParentId()));

        appResourceMapper.insertSelective(resource);
        // 给默认的超级管理员加上资源权限
        appRoleService.assignDefaultResource(appId, resourceId);

        return resourceId;
    }

    public String getResourceId(String appId, Integer type) {
        long count = this.count(appId, type);
        String resourceId = String.format("%04d", count);
        String typeName = type.equals(AppResourceTypeEnum.SYMBOL_PAGE) ? "BTN" : "PAGE";
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
