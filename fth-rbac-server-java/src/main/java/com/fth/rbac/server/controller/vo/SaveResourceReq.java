package com.fth.rbac.server.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Created on 2020/12/1 10:29 下午.
 *
 * @author fengtianhe
 */
@Data
public class SaveResourceReq {
    @ApiModelProperty("资源名称")
    @NotEmpty
    @NotNull
    private String resourceName;

    @ApiModelProperty("上级资源ID")
    private String parentId = "0";

    @ApiModelProperty("资源类型")
    @NotNull
    @NotEmpty
    private Integer type;

    @ApiModelProperty("页面Path")
    private String pageUrl;

    @ApiModelProperty("是否在目录中显示")
    private Integer inMenu;

    @ApiModelProperty("应用ID")
    private String appId;
}
