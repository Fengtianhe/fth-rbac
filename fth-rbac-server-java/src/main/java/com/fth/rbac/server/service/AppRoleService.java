package com.fth.rbac.server.service;

/**
 * Created on 2020/12/1 9:20 下午.
 *
 * @author fengtianhe
 */
public interface AppRoleService {
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
}
