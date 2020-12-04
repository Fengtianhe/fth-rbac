package com.fth.rbac.server.controller.vo;

import com.fth.rbac.server.core.entity.FrApp;
import lombok.Data;

import java.util.List;

/**
 * Created on 2020/11/28 4:10 下午.
 *
 * @author fengtianhe
 */
@Data
public class AppVo extends FrApp {
    private String creatorName;

    private List<UserInfo> developers;
}
