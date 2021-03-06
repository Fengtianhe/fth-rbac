package com.fth.rbac.server.service;

import com.fth.rbac.server.controller.vo.ResourceTreeReq;
import com.fth.rbac.server.controller.vo.ResourceTreeResp;
import com.fth.rbac.server.controller.vo.ResourceSaveReq;
import com.fth.rbac.server.controller.vo.ResourceUpdateReq;
import com.fth.rbac.server.core.entity.FrResource;
import com.fth.rbac.server.sdk.vo.MenuTreeResp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created on 2020/12/1 10:26 下午.
 *
 * @author fengtianhe
 */
public interface ResourceService {
    /**
     * 创建资源
     *
     * @param userId
     * @param resourceSaveReq
     * @return
     */
    String create(Integer userId, ResourceSaveReq resourceSaveReq);

    /**
     * 所有资源树结构
     *
     * @param resourceTreeReq
     * @return
     */
    List<ResourceTreeResp> treeAll(ResourceTreeReq resourceTreeReq);

    /**
     * 更新资源顺序
     *
     * @param resourceId
     * @param sort
     */
    void updateSort(String resourceId, Integer sort);

    /**
     * 更新状态
     *
     * @param resourceId
     * @param status
     */
    void updateStatus(String resourceId, Integer status);

    /**
     * 更新目录显示状态
     *
     * @param resourceId
     * @param inMenu
     */
    void updateInmenu(String resourceId, Integer inMenu);

    /**
     * 查询单个资源
     *
     * @param resourceId
     * @return
     */
    FrResource selectById(String resourceId);

    /**
     * 更新资源
     *
     * @param resourceUpdateReq
     */
    void update(ResourceUpdateReq resourceUpdateReq);

    /**
     * 删除资源
     *
     * @param resourceId
     */
    void deleteById(String resourceId);

    /**
     * 通过角色获取目录
     *
     * @param roleIds
     * @return
     */
    List<MenuTreeResp> getMenuByRoleId(List<String> roleIds);

    /**
     * 获取按钮资源
     *
     * @param pageUrl
     * @param asList
     * @return
     */
    List<FrResource> getBtnsByRoleId(String pageUrl, List<String> asList);

    /**
     * 导出json文件
     *
     * @param response
     */
    void exportJson(HttpServletResponse response);

    /**
     * 导入json配置
     *
     * @param file
     */
    void importJson(String appId, MultipartFile file) throws NoSuchFieldException, IllegalAccessException;
}
