package com.fth.rbac.server.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/2
 * content:
 */
@Data
public class ResourceUpdateReq {
    @ApiModelProperty("资源ID")
    private String id;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("上级资源ID")
    private String parentId = "0";

    @ApiModelProperty("页面Path")
    private String pageUrl;

    @ApiModelProperty("是否在目录中显示")
    private Integer inMenu;
}
