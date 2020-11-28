package com.fth.rbac.server.service;

import com.fth.rbac.server.controller.vo.FrAppApplicationVo;
import com.fth.rbac.server.controller.vo.SaveApplicationReq;
import com.fth.rbac.server.core.entity.FrAppApplication;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;

import java.util.List;

/**
 * Created on 2020/11/14 2:17 下午.
 *
 * @author fengtianhe
 */
public interface AppApplicationService {
    /**
     * 分页查询
     * @param request 请求参数
     * @return
     */
    PaginationResponse<FrAppApplicationVo> selectWithPagination(PaginationRequest request);

    List<FrAppApplication> selectAll();

    void add(SaveApplicationReq applicationReq, Integer userId);

    FrAppApplication selectByAppId(String appId);

}
