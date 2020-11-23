package com.fth.rbac.server.core.utils.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 冯天鹤
 * @date 2019/11/11 14:16
 */
@Data
@ApiModel
public class PaginationRequest implements Serializable {

    private static final long serialVersionUID = -4382983747881949238L;

    @ApiModelProperty(value = "当前页数")
    private Integer pageNumber = 1;
    @ApiModelProperty(value = "每页记录数")
    private Integer pageSize = 10;

}
