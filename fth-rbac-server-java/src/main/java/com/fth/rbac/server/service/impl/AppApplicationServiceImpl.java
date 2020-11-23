package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.core.entity.FrAppApplication;
import com.fth.rbac.server.core.entity.FrAppApplicationExample;
import com.fth.rbac.server.core.mapper.FrAppApplicationMapper;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.AppApplicationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2020/11/14 2:17 下午.
 *
 * @author fengtianhe
 */
@Service
public class AppApplicationServiceImpl implements AppApplicationService {
    @Autowired
    private FrAppApplicationMapper frAppApplicationMapper;

    @Override
    public PaginationResponse<FrAppApplication> selectWithPagination(PaginationRequest request) {
        Page page = PageHelper.startPage(request.getPageNumber(), request.getPageSize());
        FrAppApplicationExample example = new FrAppApplicationExample();
        List<FrAppApplication> frAppApplications = frAppApplicationMapper.selectByExample(example);
        return new PaginationResponse<>(frAppApplications, page);
    }
}
