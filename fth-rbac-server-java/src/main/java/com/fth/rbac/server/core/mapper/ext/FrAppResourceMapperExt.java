package com.fth.rbac.server.core.mapper.ext;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/2
 * content:
 */
@Mapper
public interface FrAppResourceMapperExt {
    void updateSiblingSort(@Param("appId") String appId, @Param("resourceId") String resourceId,
                           @Param("parentId") String parentId, @Param("minSort") int minSort,
                           @Param("maxSort") int maxSort, @Param("change") int change);
}
