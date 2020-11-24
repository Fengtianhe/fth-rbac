package com.fth.rbac.server.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 54570
 * @version 1.0
 * @date 2020/9/9
 * content:
 */
@Data
@ApiModel
public class LoginReq {
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;
}
