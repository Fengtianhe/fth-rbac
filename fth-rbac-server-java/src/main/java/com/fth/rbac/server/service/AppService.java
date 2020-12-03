package com.fth.rbac.server.service;

import com.fth.rbac.server.controller.vo.FrAppVo;
import com.fth.rbac.server.controller.vo.SaveApplicationReq;
import com.fth.rbac.server.core.entity.FrApp;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;

import java.util.List;

/**
 * Created on 2020/11/14 2:17 下午.
 *
 * @author fengtianhe
 */
public interface AppService {
    /**
     * 分页查询
     *
     * @param request 请求参数
     * @return
     */
    PaginationResponse<FrAppVo> selectWithPagination(PaginationRequest request);

    List<FrApp> selectAll(Integer userId);

    void add(SaveApplicationReq applicationReq, Integer userId);

    FrApp selectByAppId(String appId);

}
