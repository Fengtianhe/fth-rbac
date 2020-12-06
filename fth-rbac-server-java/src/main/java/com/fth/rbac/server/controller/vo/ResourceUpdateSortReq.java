package com.fth.rbac.server.controller.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/2
 * content:
 */
@Data
public class ResourceUpdateSortReq implements Serializable {
    @NotNull
    @NotEmpty
    private String resourceId;
    @NotNull
    @NotEmpty
    private Integer sort;

    public ResourceUpdateSortReq() {

    }
}
