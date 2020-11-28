package com.fth.rbac.server.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Created on 2020/11/28 2:23 下午.
 *
 * @author fengtianhe
 */
@Data
@ApiModel
public class SaveApplicationReq {
    @ApiModelProperty("应用ID")
    @NotNull
    @NotEmpty
    private String appId;

    @ApiModelProperty("应用名称")
    @NotNull
    @NotEmpty
    private String appName;
}
