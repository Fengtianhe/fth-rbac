package com.fth.rbac.server.controller;

import com.fth.rbac.server.aop.PassToken;
import com.fth.rbac.server.controller.vo.LoginReq;
import com.fth.rbac.server.controller.vo.UserInfo;
import com.fth.rbac.server.controller.vo.UserUpdateReq;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.utils.SecurityHelper;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.core.utils.redis.RedisHelper;
import com.fth.rbac.server.service.UserService;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
@Api(tags = "系统管理-用户")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisHelper redisHelper;

    @PostMapping("/login")
    @ApiOperation("登录接口")
    @PassToken
    public CommonResponse login(@RequestBody LoginReq loginReq) {
        verifyCaptcha(loginReq.getUsername(), loginReq.getCaptcha());
        String res = userService.login(loginReq);
        return CommonResponse.withSuccessResp(res);
    }

    @GetMapping("/info")
    @ApiOperation("当前用户信息")
    public CommonResponse info(HttpServletRequest request) {
        Integer userId = SecurityHelper.userId(request);
        UserInfo userInfo = userService.selectInfoById(userId);
        return CommonResponse.withSuccessResp(userInfo);
    }

    @PutMapping("")
    @ApiOperation("更新用户信息")
    public CommonResponse update(@RequestBody UserUpdateReq updateReq, HttpServletRequest request) {
        Integer userId = SecurityHelper.userId(request);
        userService.updateById(userId, updateReq);
        return CommonResponse.withSuccessResp(true);
    }

    @GetMapping("/captcha")
    @PassToken
    public void getCode(@RequestParam String username, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(username)) {
            return;
        }
        // 设置宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(200, 40, 5);
        String text = specCaptcha.text();
        redisHelper.set("VERIFY_CODE" + "_" + username, text, 5 * 60, TimeUnit.SECONDS);
        specCaptcha.out(response.getOutputStream());
    }

    @GetMapping("/list")
    @ApiOperation("用户列表")
    public CommonResponse<PaginationResponse<UserInfo>> list(@RequestParam PaginationRequest request) {
        PaginationResponse<UserInfo> lists = userService.selectWithPagination(request);
        return CommonResponse.withSuccessResp(lists);
    }

    @GetMapping("/by-keywords")
    @ApiOperation("通过关键词查询")
    public CommonResponse<List<UserInfo>> queryByKeywords(@RequestParam String keywords){
        List<UserInfo> users = userService.queryByKeywords(keywords);
        return CommonResponse.withSuccessResp(users);
    }

    private void verifyCaptcha(String username, String captcha) {
        String key = "VERIFY_CODE" + "_" + username;
        String code = redisHelper.get(key);
        if (!captcha.equalsIgnoreCase(code)) {
            throw new CommonException(ExceptionCodes.LOGIN_CAPTCHA_ERROR);
        }
    }
}
