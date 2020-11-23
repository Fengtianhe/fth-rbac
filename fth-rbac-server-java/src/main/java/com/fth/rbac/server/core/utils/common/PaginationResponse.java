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
    private List<T> content;
    @ApiModelProperty("总页数")
    private Integer totalPages;
    @ApiModelProperty("总条数")
    private Long totalElements;
    @ApiModelProperty("单页请求条数")
    private Integer size;
    @ApiModelProperty("当前页码")
    private Integer requestPager;

    public PaginationResponse() {
    }

    public PaginationResponse(List<T> list, Page page) {
        this.totalElements = page.getTotal();
        this.totalPages = page.getPages();
        this.size = page.getPageSize();
        this.requestPager = page.getPageNum();
        this.content = list;
    }
}