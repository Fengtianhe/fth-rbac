package com.fth.rbac.server.sdk.vo;

import com.fth.rbac.server.core.entity.FrResource;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/14
 * content:
 */
@Data
public class BtnResourceResp {
    private String appId;

    private String id;

    private String name;

    public static List<BtnResourceResp> covert(List<FrResource> resources){
        List<BtnResourceResp> resourceResps = new ArrayList<>();
        BtnResourceResp resp ;
        for (FrResource resource : resources) {
            resp = new BtnResourceResp();
            resp.setAppId(resource.getAppId());
            resp.setId(resource.getId());
            resp.setName(resource.getResourceName());

            resourceResps.add(resp);
        }
        return resourceResps;
    }
}
