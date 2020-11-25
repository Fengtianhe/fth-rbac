package com.fth.rbac.server.aop;

import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.core.utils.common.ResponseConstant;
import com.fth.rbac.server.core.utils.date.DatePattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.SocketTimeoutException;

/**
 * Created on 2020/11/11 10:12 下午.
 *
 * @author fengtianhe
 */
@RestControllerAdvice
@Slf4j
public class FthRbacAdvice {
    /**
     * 通用业务报错处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CommonException.class)
    public CommonResponse<?> commonException(CommonException e) {
        return CommonResponse.withResp(e.getCode(), e.getMsg());
    }
    /**
     * 连接超时
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SocketTimeoutException.class)
    public CommonResponse<?> socketTimeoutException(SocketTimeoutException e) {
        return CommonResponse.withResp(ResponseConstant.RESP_ERROR_CODE, "服务连接超时：" + e.getMessage());
    }

    /**
     * 公共异常处理，将异常信息储存到数据库
     *
     * @param e 异常
     * @return 友好的响应体
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse<?> commonException(Exception e) {
        // eg: java.lang.NullPointerException
        String type = e.toString();
        // eg: null
        String message = e.getMessage();
        // 错误对战信息
        StackTraceElement[] stackTrace = e.getStackTrace();
        // eg: com.bridgeintelligent.tag.webserver.system.server.serviceimpl.CmnFileServiceImpl.downFile(CmnFileServiceImpl.java:80)
        String stackTraceElement = stackTrace[0].toString();
        // 把堆栈信息以string的方式拿到
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String exception = sw.toString();


//        SysException saveData = new SysException();
//        saveData.setType(type)
//                .setException(exception.length() > 1000 ? exception.substring(0, 1000) : exception)
//                .setCreator(getUsername())
//                .setCreatedAt(CommonDateUtil.format(DatePattern.YYYYMMDDHHMMSS));
//        sysExceptionService.save(saveData);

        log.error("TagAdvice.Exception：{}", message, e);
        return CommonResponse.withResp(ResponseConstant.RESP_ERROR_CODE, "系统错误：" + type);
    }

}
