package com.fth.rbac.server.sdk;

import com.fth.rbac.server.core.entity.FrResource;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCode;
import com.fth.rbac.server.core.utils.common.BaseController;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.sdk.vo.BtnResourceReq;
import com.fth.rbac.server.sdk.vo.BtnResourceResp;
import com.fth.rbac.server.sdk.vo.MenuTreeResp;
import com.fth.rbac.server.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2020/12/6 1:53 下午.
 *
 * @author fengtianhe
 */
@Api(tags = "SDK")
@RestController
@RequestMapping("/sdk/resource")
public class SdkResourceController extends BaseController {
    @Autowired
    private ResourceService resourceService;

    @ApiOperation("获取目录")
    @GetMapping("/menu")
    public CommonResponse<List<MenuTreeResp>> menu(@RequestParam String roleId) {
        if(StringUtils.isEmpty(roleId)){
            throw new CommonException(new ExceptionCode(401, "请求参数验证失败,详情：角色Id不能为空"));
        }
        String[] roleIds = roleId.split(",");
        List<MenuTreeResp> menu = resourceService.getMenuByRoleId(Arrays.asList(roleIds));
        return CommonResponse.withSuccessResp(menu);
    }

    @ApiOperation("获取按钮资源")
    @GetMapping("/btn")
    public CommonResponse<List<BtnResourceResp>> btns(@ModelAttribute BtnResourceReq resourceReq) {
        validateData(resourceReq);
        String[] roleIds = resourceReq.getRoleId().split(",");
        List<FrResource> btns = resourceService.getBtnsByRoleId(resourceReq.getPageUrl(), Arrays.asList(roleIds));
        return CommonResponse.withSuccessResp(BtnResourceResp.covert(btns));
    }
}
