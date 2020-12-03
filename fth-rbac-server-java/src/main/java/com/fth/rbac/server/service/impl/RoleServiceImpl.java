package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.core.entity.FrRole;
import com.fth.rbac.server.core.entity.FrRoleResource;
import com.fth.rbac.server.core.entity.FrRoleResourceExample;
import com.fth.rbac.server.core.mapper.FrRoleMapper;
import com.fth.rbac.server.core.mapper.FrRoleResourceMapper;
import com.fth.rbac.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created on 2020/12/1 9:20 下午.
 *
 * @author fengtianhe
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private FrRoleMapper appRoleMapper;
    @Autowired
    private FrRoleResourceMapper FrRoleResourceMapper;

    @Override
    public void createDefaultRole(String appId) {
        String roleId = this.getAdminId(appId);

        FrRole role = new FrRole();
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
        FrRoleResource saveData = new FrRoleResource();
        saveData.setRoleId(adminRoleId);
        saveData.setResourceId(resourceId);

        FrRoleResourceMapper.insertSelective(saveData);
    }

    @Override
    public void deleteByResourceId(String resId) {
        FrRoleResourceExample example = new FrRoleResourceExample();
        example.createCriteria().andResourceIdEqualTo(resId);
        FrRoleResourceMapper.deleteByExample(example);
    }

    public String getAdminId(String appId) {
        return appId + "_ADMIN";
    }
}
