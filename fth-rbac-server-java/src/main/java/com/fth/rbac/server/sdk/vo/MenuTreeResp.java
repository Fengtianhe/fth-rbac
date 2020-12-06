package com.fth.rbac.server.sdk.vo;

import com.fth.rbac.server.controller.vo.ResourceTreeResp;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2020/12/6 2:07 下午.
 *
 * @author fengtianhe
 */
@Data
public class MenuTreeResp {
    private String id;

    private String pageUrl;

    private String name;

    private List<MenuTreeResp> children;

    public static List<MenuTreeResp> covert(List<ResourceTreeResp> resources) {
        List<MenuTreeResp> menuTreeResp = new ArrayList<>();
        MenuTreeResp menuTree;
        if (CollectionUtils.isEmpty(resources)) {
            return null;
        }

        for (ResourceTreeResp resource : resources) {
            menuTree = new MenuTreeResp();
            menuTree.setId(resource.getId());
            menuTree.setName(resource.getResourceName());
            menuTree.setPageUrl(resource.getPageUrl());
            menuTree.setChildren(covert(resource.getChildren()));
            menuTreeResp.add(menuTree);
        }

        return menuTreeResp;
    }
}
