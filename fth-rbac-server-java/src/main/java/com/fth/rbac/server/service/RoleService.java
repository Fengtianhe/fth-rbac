package com.fth.rbac.server.service;

import com.fth.rbac.server.controller.vo.RoleAssignReq;
import com.fth.rbac.server.controller.vo.RoleListReq;
import com.fth.rbac.server.core.entity.FrRole;
import com.fth.rbac.server.core.utils.common.PaginationResponse;

import java.util.List;

/**
 * Created on 2020/12/1 9:20 下午.
 *
 * @author fengtianhe
 */
public interface RoleService {
    /**
     * 创建默认角色
     *
     * @param appId
     */
    void createDefaultRole(String appId);

    /**
     * 给默认角色赋权
     *
     * @param appId
     * @param resourceId
     */
    void assignDefaultResource(String appId, String resourceId);

    /**
     * 删除资源关联的数据
     *
     * @param resId
     */
    void deleteByResourceId(String resId);

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    PaginationResponse<FrRole> selectWithPagination(RoleListReq request);

    /**
     * 分配资源
     *
     * @param request
     * @param userId
     */
    void assignResource(RoleAssignReq request, Integer userId);

    FrRole selectByRoleId(String roleId);

    void updateRole(String roleId, FrRole updateData);

    /**
     * 获取已分配的资源
     *
     * @param roleId
     * @return
     */
    List<String> selectAssignResourceIds(String roleId);

    /**
     * 通过角色查询资源ID集合
     * @param roleIds
     * @return
     */
    List<String> selectResourceIdsByRoleIds(List<String> roleIds);
}
