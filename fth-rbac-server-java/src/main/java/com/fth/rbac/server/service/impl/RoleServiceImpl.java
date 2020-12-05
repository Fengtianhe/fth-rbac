package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.controller.vo.RoleAssignReq;
import com.fth.rbac.server.controller.vo.RoleListReq;
import com.fth.rbac.server.core.entity.FrRole;
import com.fth.rbac.server.core.entity.FrRoleExample;
import com.fth.rbac.server.core.entity.FrRoleResource;
import com.fth.rbac.server.core.entity.FrRoleResourceExample;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCode;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.mapper.FrRoleMapper;
import com.fth.rbac.server.core.mapper.FrRoleResourceMapper;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private FrRoleResourceMapper roleResourceMapper;

    @Override
    public void createDefaultRole(String appId) {
        String roleId = this.getAdminId(appId);

        FrRole role = new FrRole();
        role.setAppId(appId);
        role.setRoleName("超级管理员");
        role.setId(roleId);
        role.setCreatedAt(new Date());
        role.setCreator(0);
        appRoleMapper.insertSelective(role);
    }

    @Override
    public PaginationResponse<FrRole> selectWithPagination(RoleListReq request) {
        Page page = PageHelper.startPage(request.getPageNumber(), request.getPageSize());
        FrRoleExample example = new FrRoleExample();
        example.createCriteria().andAppIdEqualTo(request.getAppId());
        List<FrRole> frRoles = appRoleMapper.selectByExample(example);
        return new PaginationResponse<>(frRoles, page);
    }

    @Override
    public void assignResource(RoleAssignReq request, Integer userId) {
        if (StringUtils.isEmpty(request.getRoleId())) {
            this.createAssignRole(request, userId);
        } else {
            this.updateAssignRole(request);
        }
    }

    @Override
    public FrRole selectByRoleId(String roleId) {
        FrRoleExample example = new FrRoleExample();
        example.createCriteria().andIdEqualTo(roleId);
        List<FrRole> frRoles = appRoleMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(frRoles)) {
            return null;
        }
        return frRoles.get(0);
    }

    @Override
    public void updateRole(String roleId, FrRole updateData) {
        FrRoleExample example = new FrRoleExample();
        example.createCriteria().andIdEqualTo(roleId);
        appRoleMapper.updateByExample(updateData, example);
    }

    @Override
    public List<String> selectAssignResourceIds(String roleId) {
        FrRoleResourceExample example = new FrRoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        return roleResourceMapper.selectByExample(example)
                .stream()
                .map(FrRoleResource::getResourceId)
                .collect(Collectors.toList());
    }

    private void updateAssignRole(RoleAssignReq request) {
        this.checkAdminRole(request.getAppId(), request.getRoleId());

        FrRole role = this.selectByRoleId(request.getRoleId());
        if (!request.getRoleName().equals(role.getRoleName())) {
            FrRole updateData = new FrRole();
            updateData.setRoleName(request.getRoleName());
            this.updateRole(request.getRoleId(), updateData);
        }

        this.deleteAssignByRoleId(request.getRoleId());
        this.assignResource(request.getRoleId(), request.getResourceIds());
    }

    /**
     * 检查是否是默认角色
     *
     * @param appId
     */
    private void checkAdminRole(String appId, String roleId) {
        if (roleId.equals(this.getAdminId(appId))) {
throw new CommonException(ExceptionCodes.ROLE_SUPER_CANNOT_UPT);
        }
    }

    private void deleteAssignByRoleId(String roleId) {
        FrRoleResourceExample example = new FrRoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(example);
    }

    private void createAssignRole(RoleAssignReq request, Integer userId) {
        String roleId = this.getRoleId(request.getAppId());
        FrRole role = new FrRole();
        role.setAppId(request.getAppId());
        role.setId(roleId);
        role.setRoleName(request.getRoleName());
        role.setCreatedAt(new Date());
        role.setCreator(userId);
        appRoleMapper.insertSelective(role);
        //保存关联关系
        this.assignResource(roleId, request.getResourceIds());
    }

    private void assignResource(String roleId, List<String> resourceIds) {
        FrRoleResource roleResource;
        for (String resourceId : resourceIds) {
            roleResource = new FrRoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            roleResourceMapper.insertSelective(roleResource);
        }
    }

    private String getRoleId(String appId) {
        long roleCount = this.countByAppId(appId);
        String roleId = String.format("%04d", roleCount);
        return appId + "_" + roleId;
    }

    private long countByAppId(String appId) {
        FrRoleExample example = new FrRoleExample();
        example.createCriteria().andAppIdEqualTo(appId);
        return appRoleMapper.countByExample(example);
    }

    @Override
    public void assignDefaultResource(String appId, String resourceId) {
        String adminRoleId = this.getAdminId(appId);
        FrRoleResource saveData = new FrRoleResource();
        saveData.setRoleId(adminRoleId);
        saveData.setResourceId(resourceId);

        roleResourceMapper.insertSelective(saveData);
    }

    @Override
    public void deleteByResourceId(String resId) {
        FrRoleResourceExample example = new FrRoleResourceExample();
        example.createCriteria().andResourceIdEqualTo(resId);
        roleResourceMapper.deleteByExample(example);
    }

    public String getAdminId(String appId) {
        return appId + "_ADMIN";
    }
}
