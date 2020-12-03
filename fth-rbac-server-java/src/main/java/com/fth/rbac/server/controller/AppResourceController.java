package com.fth.rbac.server.controller;

import com.fth.rbac.server.controller.vo.*;
import com.fth.rbac.server.core.entity.FrResource;
import com.fth.rbac.server.core.utils.SecurityHelper;
import com.fth.rbac.server.core.utils.common.BaseController;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 2020/12/1 8:22 下午.
 *
 * @author fengtianhe
 */
@Api(tags = "应用管理-资源")
@RestController
@RequestMapping("/api/app/resource")
public class AppResourceController extends BaseController {
    @Autowired
    private ResourceService resourceService;

    @ApiOperation("添加资源")
    @PostMapping("")
    public CommonResponse<String> create(@RequestBody ResourceSaveReq resourceSaveReq, HttpServletRequest request) {
        Integer userId = SecurityHelper.userId(request);

        String resourceId = resourceService.create(userId, resourceSaveReq);
        return CommonResponse.withSuccessResp(resourceId);
    }

    @ApiOperation("编辑资源")
    @PutMapping("")
    public CommonResponse<Boolean> update(@RequestBody ResourceUpdateReq resourceUpdateReq) {
        resourceService.update(resourceUpdateReq);
        return CommonResponse.withSuccessResp(true);
    }

    @ApiOperation("资源列表-所有")
    @GetMapping("/tree-all")
    public CommonResponse all(@ModelAttribute ResourceTreeReq resourceTreeReq) {
        List<ResourceTreeResp> tree = resourceService.treeAll(resourceTreeReq);
        return CommonResponse.withSuccessResp(tree);
    }

    @ApiOperation("更新资源排序")
    @PutMapping("/sort")
    public CommonResponse<Boolean> updateSort(@RequestBody ResourceUpdateSortReq req) {
        validateData(req);
        resourceService.updateSort(req.getResourceId(), req.getSort());
        return CommonResponse.withSuccessResp(true);
    }

    @ApiOperation("更新资源状态")
    @PutMapping("/status")
    public CommonResponse<Boolean> updateStatus(@RequestBody ResourceUpdateStatusReq req) {
        validateData(req);
        resourceService.updateStatus(req.getResourceId(), req.getStatus());
        return CommonResponse.withSuccessResp(true);
    }

    @ApiOperation("更新菜单显示状态")
    @PutMapping("/inmenu")
    public CommonResponse<Boolean> updateInmenu(@RequestBody ResourceUpdateMenuReq req) {
        validateData(req);
        resourceService.updateInmenu(req.getResourceId(), req.getInMenu());
        return CommonResponse.withSuccessResp(true);
    }

    @ApiOperation("资源详情")
    @GetMapping("/resource")
    public CommonResponse<FrResource> updateInmenu(@RequestParam String resourceId) {
        FrResource resource = resourceService.selectById(resourceId);
        return CommonResponse.withSuccessResp(resource);
    }

    @ApiOperation("删除资源")
    @DeleteMapping("")
    public CommonResponse<Boolean> deleteResource(@RequestParam String resourceId) {
        resourceService.deleteById(resourceId);
        return CommonResponse.withSuccessResp(true);
    }
}
