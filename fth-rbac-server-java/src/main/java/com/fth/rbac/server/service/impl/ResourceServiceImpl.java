package com.fth.rbac.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.fth.rbac.server.controller.vo.ResourceTreeReq;
import com.fth.rbac.server.controller.vo.ResourceTreeResp;
import com.fth.rbac.server.controller.vo.ResourceSaveReq;
import com.fth.rbac.server.controller.vo.ResourceUpdateReq;
import com.fth.rbac.server.core.entity.FrResource;
import com.fth.rbac.server.core.entity.FrResourceExample;
import com.fth.rbac.server.core.enums.ResourceInmenuEnum;
import com.fth.rbac.server.core.enums.ResourceStatusEnum;
import com.fth.rbac.server.core.enums.ResourceTypeEnum;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.mapper.FrResourceMapper;
import com.fth.rbac.server.core.mapper.ext.FrResourceMapperExt;
import com.fth.rbac.server.core.utils.CommonFileHelper;
import com.fth.rbac.server.core.utils.TreeHelper;
import com.fth.rbac.server.sdk.vo.MenuTreeResp;
import com.fth.rbac.server.service.ResourceService;
import com.fth.rbac.server.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleResult;
import javax.servlet.http.HttpServletResponse;
import java.awt.Menu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2020/12/1 10:26 下午.
 *
 * @author fengtianhe
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private FrResourceMapper appResourceMapper;
    @Autowired
    private FrResourceMapperExt appResourceMapperExt;
    @Autowired
    private RoleService roleService;

    @Override
    public String create(Integer userId, ResourceSaveReq resourceSaveReq) {
        FrResource resource = new FrResource();
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
        roleService.assignDefaultResource(appId, resourceId);

        return resourceId;
    }

    @Override
    public List<ResourceTreeResp> treeAll(ResourceTreeReq resourceTreeReq) {
        FrResourceExample example = new FrResourceExample();
        FrResourceExample.Criteria criteria = example.createCriteria();
        criteria.andAppIdEqualTo(resourceTreeReq.getAppId());
        if (resourceTreeReq.getType() != null) {
            criteria.andTypeEqualTo(resourceTreeReq.getType());
        }
        if (resourceTreeReq.getStatus() != null) {
            criteria.andStatusEqualTo(resourceTreeReq.getStatus());
        }
        // 按sort正排
        example.setOrderByClause("sort");
        List<FrResource> FrResources = appResourceMapper.selectByExample(example);
        return this.covertToTree(FrResources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSort(String resourceId, Integer sort) {
        if (StringUtils.isEmpty(resourceId) || sort == null) {
            throw new CommonException(ExceptionCodes.RESOURCE_UNKNOWN_PARAMS);
        }

        FrResource oldResource = appResourceMapper.selectByPrimaryKey(resourceId);
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
        FrResource updateData = new FrResource();
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
        if (ResourceStatusEnum.SYMBOL_ENABLED.equals(status)) {
            this.enableResource(resourceId);
        } else if (ResourceStatusEnum.SYMBOL_DISABLED.equals(status)) {
            this.disabledResource(resourceId);
        }
    }

    @Override
    public void updateInmenu(String resourceId, Integer inMenu) {
        FrResource updateData = new FrResource();
        updateData.setId(resourceId);
        updateData.setInMenu(inMenu);
        appResourceMapper.updateByPrimaryKeySelective(updateData);
    }

    @Override
    public FrResource selectById(String resourceId) {
        return appResourceMapper.selectByPrimaryKey(resourceId);
    }

    @Override
    public void update(ResourceUpdateReq resourceUpdateReq) {
        FrResource resource = new FrResource();

        resource.setId(resourceUpdateReq.getId());
        resource.setInMenu(resourceUpdateReq.getInMenu());
        resource.setPageUrl(resourceUpdateReq.getPageUrl());
        resource.setResourceName(resourceUpdateReq.getName());
        resource.setParentId(resourceUpdateReq.getParentId());

        appResourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    public void deleteById(String resourceId) {
        FrResource resource = appResourceMapper.selectByPrimaryKey(resourceId);
        List<FrResource> subList = new ArrayList<>();
        subList.add(resource);
        subList = this.querySubList(subList);

        for (FrResource FrResource : subList) {
            String resId = FrResource.getId();
            appResourceMapper.deleteByPrimaryKey(resId);
            roleService.deleteByResourceId(resId);
        }
    }

    @Override
    public List<MenuTreeResp> getMenuByRoleId(List<String> roleIds) {
        List<String> resourceIds = roleService.selectResourceIdsByRoleIds(roleIds);
        List<FrResource> resources = this.getByResourceIds(resourceIds);
        // 过滤出 [显示] 在目录中的 [启用] 的 [页面] 资源
        resources = resources.stream().filter(res ->
                res.getType().equals(ResourceTypeEnum.SYMBOL_PAGE)
                        && res.getInMenu().equals(ResourceInmenuEnum.SYMBOL_SHOW)
                        && res.getStatus().equals(ResourceStatusEnum.SYMBOL_ENABLED)
        ).collect(Collectors.toList());
        return MenuTreeResp.covert(this.covertToTree(resources));
    }

    @Override
    public List<FrResource> getBtnsByRoleId(String pageUrl, List<String> roleIds) {
        List<String> resourceIds = roleService.selectResourceIdsByRoleIds(roleIds);
        // 校验是否有当前页面路径权限
        FrResourceExample example = new FrResourceExample();
        example.createCriteria()
                .andPageUrlEqualTo(pageUrl)
                .andIdIn(resourceIds);
        List<FrResource> frResources = appResourceMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(frResources)) {
            throw new CommonException(ExceptionCodes.RESOURCE_PAGE_AUTH_FAILURE).args(pageUrl);
        }

        List<FrResource> btnResource = this.selectBtnByResourceId(frResources.get(0).getId());
        return btnResource;
    }

    @Override
    public void exportJson(HttpServletResponse response) {
        List<FrResource> list = appResourceMapper.selectByExample(new FrResourceExample());
        List<ResourceTreeResp> resultList = this.covertToTree(list);

        String filepath = CommonFileHelper.createJsonFile(JSON.toJSONString(resultList), "资源配置");
        if (filepath == null) {
            throw new CommonException(ExceptionCodes.RESOURCE_EXPORT_FAILURE);
        }
        CommonFileHelper.downloadByPath(filepath, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importJson(String appId, MultipartFile file) throws NoSuchFieldException, IllegalAccessException {
        if (file == null) {
            throw new CommonException(ExceptionCodes.RESOURCE_IMPORT_UNKNOW_FILE);
        }
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            sb = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {
                // 去除空格，防止格式化有字段过长
                sb.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        List<ResourceTreeResp> resourceTree = JSON.parseArray(sb.toString(), ResourceTreeResp.class);
        List<ResourceTreeResp> resources = TreeHelper.open(resourceTree, "children");

        // 清除管理员权限
        String adminRoleId = roleService.getAdminId(appId);
        roleService.deleteAssignByRoleId(adminRoleId);
        // 清除Application下的所有资源
        FrResourceExample example = new FrResourceExample();
        example.createCriteria().andAppIdEqualTo(appId);
        appResourceMapper.deleteByExample(example);

        resources.forEach(res -> {
            // 插入资源数据
            appResourceMapper.insertSelective(res);
            // 插入管理员所有资源权限
            roleService.assignDefaultResource(appId, res.getId());
        });
    }

    private List<FrResource> getByResourceIds(List<String> resourceIds) {
        FrResourceExample example = new FrResourceExample();
        example.createCriteria().andIdIn(resourceIds);
        example.setOrderByClause("sort");
        return appResourceMapper.selectByExample(example);
    }

    /**
     * 停用资源，停用所有子资源
     *
     * @param resourceId
     */
    private void disabledResource(String resourceId) {
        FrResource resource = appResourceMapper.selectByPrimaryKey(resourceId);
        List<FrResource> subList = new ArrayList<>();
        subList.add(resource);
        subList = this.querySubList(subList);

        FrResource updateData;
        for (FrResource FrResource : subList) {
            updateData = new FrResource();
            updateData.setId(FrResource.getId());
            updateData.setStatus(ResourceStatusEnum.SYMBOL_DISABLED);
            appResourceMapper.updateByPrimaryKeySelective(updateData);
        }
    }

    /**
     * 查询子资源
     *
     * @param resources
     * @return
     */
    private List<FrResource> querySubList(List<FrResource> resources) {
        FrResourceExample example = new FrResourceExample();
        FrResourceExample.Criteria criteria = example.createCriteria();
        List<FrResource> result = new ArrayList<>();
        if (resources != null && resources.size() > 0) {
            result.addAll(resources);
            for (FrResource tagMenu : resources) {
                if (tagMenu.getType().equals(ResourceTypeEnum.SYMBOL_PAGE)) {
                    criteria.andParentIdEqualTo(tagMenu.getId());
                    List<FrResource> list = appResourceMapper.selectByExample(example);
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
        FrResource resource = appResourceMapper.selectByPrimaryKey(resourceId);
        // 如果不是顶级资源，就一直往上找, 依次启用
        FrResource updateData = new FrResource();
        updateData.setId(resource.getId());
        updateData.setStatus(ResourceStatusEnum.SYMBOL_ENABLED);
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
    public List<ResourceTreeResp> covertToTree(List<FrResource> resources) {
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
        String typeName = type.equals(ResourceTypeEnum.SYMBOL_PAGE) ? "PAGE" : "BTN";
        return "RES_" + typeName + "_" + appId + "_" + resourceId;
    }

    public long count(String appId, Integer type) {
        FrResourceExample example = new FrResourceExample();
        example.createCriteria()
                .andAppIdEqualTo(appId)
                .andTypeEqualTo(type);
        long l = appResourceMapper.countByExample(example);
        return l;
    }

    private Integer getResourceSort(String appId, Integer type, String parentId) {
        FrResourceExample example = new FrResourceExample();
        example.createCriteria()
                .andAppIdEqualTo(appId)
                .andTypeEqualTo(type)
                .andParentIdEqualTo(parentId);
        List<FrResource> FrResources = appResourceMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(FrResources)) {
            return 0;
        }
        FrResource resource = FrResources.stream()
                .max(Comparator.comparing(FrResource::getSort))
                .get();
        return resource.getSort() + 1;
    }

    private List<FrResource> selectBtnByResourceId(String resourceId) {
        FrResourceExample example = new FrResourceExample();
        example.createCriteria()
                .andTypeEqualTo(ResourceTypeEnum.SYMBOL_BTN)
                .andParentIdEqualTo(resourceId);
        return appResourceMapper.selectByExample(example);
    }
}
