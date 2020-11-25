package com.fth.rbac.server.core.utils.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 冯天鹤
 * @date 2019/11/8 11:13
 */
@Data
@ApiModel(value = "CommonResponse", description = "通用返回模板")
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = 3380370321354738645L;

    @ApiModelProperty(required = true, value = "状态码")
    private int code;

    @ApiModelProperty(required = true, value = "状态描述")
    private String message;

    @ApiModelProperty(required = true, value = "业务数据")
    private T data;

    /*========================================================
                    获取返回的公共参数处理
    ========================================================*/
    public static <T> CommonResponse<T> withSuccessResp(T data) {
        CommonResponse<T> cr = new CommonResponse<>();
        cr.setCode(ResponseConstant.RESP_SUCC_CODE);
        cr.setMessage(ResponseConstant.RESP_SUCC_MESG);
        cr.setData(data);
        return cr;
    }

    public static <T> CommonResponse<T> withErrorResp(String msg) {
        CommonResponse<T> cr = new CommonResponse<>();
        cr.setCode(ResponseConstant.RESP_ERROR_CODE);
        cr.setMessage(msg);
        return cr;
    }

    public static <T> CommonResponse<T> withResp(int code, String msg) {
        CommonResponse<T> cr = new CommonResponse<>();
        cr.setCode(code);
        cr.setMessage(msg);
        return cr;
    }

    public static <T> CommonResponse<T> withResp(int code, String msg, T data) {
        CommonResponse<T> cr = new CommonResponse<>();
        cr.setCode(code);
        cr.setMessage(msg);
        cr.setData(data);
        return cr;
    }
}