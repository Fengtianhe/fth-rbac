package com.fth.rbac.server.controller.vo;

import com.fth.rbac.server.core.entity.FrApp;
import lombok.Data;

/**
 * Created on 2020/11/28 4:10 下午.
 *
 * @author fengtianhe
 */
@Data
public class FrAppVo extends FrApp {
    private String creatorName;
}
