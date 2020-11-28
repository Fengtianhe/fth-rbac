package com.fth.rbac.server.core.utils.common;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PaginationResponse<T> {
    @ApiModelProperty("列表数据")
    private List<T> lists;
    @ApiModelProperty("总页数")
    private Integer pages;
    @ApiModelProperty("总条数")
    private Long total;
    @ApiModelProperty("单页请求条数")
    private Integer size;
    @ApiModelProperty("当前页码")
    private Integer pageNum;

    public PaginationResponse() {
    }

    public PaginationResponse(List<T> list, Page page) {
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.size = page.getPageSize();
        this.pageNum = page.getPageNum();
        this.lists = list;
    }
}