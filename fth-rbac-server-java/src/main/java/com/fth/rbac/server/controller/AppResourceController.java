package com.fth.rbac.server.controller;

import com.fth.rbac.server.controller.vo.SaveResourceReq;
import com.fth.rbac.server.core.utils.SecurityHelper;
import com.fth.rbac.server.core.utils.common.BaseController;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.service.AppResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    private AppResourceService appResourceService;

    @ApiOperation("添加资源")
    @PostMapping("")
    public CommonResponse<String> create(@RequestBody SaveResourceReq saveResourceReq, HttpServletRequest request) {
        Integer userId = SecurityHelper.userId(request);

        String resourceId = appResourceService.create(userId, saveResourceReq);
        return CommonResponse.withSuccessResp(resourceId);
    }
}
