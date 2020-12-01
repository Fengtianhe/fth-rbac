package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.core.entity.FrAppRole;
import com.fth.rbac.server.core.entity.FrAppRoleResource;
import com.fth.rbac.server.core.mapper.FrAppRoleMapper;
import com.fth.rbac.server.core.mapper.FrAppRoleResourceMapper;
import com.fth.rbac.server.service.AppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created on 2020/12/1 9:20 下午.
 *
 * @author fengtianhe
 */
@Service
public class AppRoleServiceImpl implements AppRoleService {
    @Autowired
    private FrAppRoleMapper appRoleMapper;
    @Autowired
    private FrAppRoleResourceMapper frAppRoleResourceMapper;

    @Override
    public void createDefaultRole(String appId) {
        String roleId = this.getAdminId(appId);

        FrAppRole role = new FrAppRole();
        role.setAppId(appId);
        role.setRoleName("超级管理员");
        role.setId(roleId);
        role.setCreatedAt(new Date());
        role.setCreator(0);
        role.setIdPath("," + roleId + ",");
        role.setRoleName(",超级管理员,");
        appRoleMapper.insertSelective(role);
    }

    @Override
    public void assignDefaultResource(String appId, String resourceId) {
        String adminRoleId = this.getAdminId(appId);
        FrAppRoleResource saveData = new FrAppRoleResource();
        saveData.setRoleId(adminRoleId);
        saveData.setResourceId(resourceId);

        frAppRoleResourceMapper.insertSelective(saveData);
    }

    public String getAdminId(String appId) {
        return appId + "_ADMIN";
    }
}
