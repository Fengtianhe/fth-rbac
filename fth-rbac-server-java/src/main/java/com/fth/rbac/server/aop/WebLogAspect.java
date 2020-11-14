package com.fth.rbac.server.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 入参 出参日志打印
 * @author fengtianhe
 * @version $Id: WebLogAspect.java, v 0.1 2018年10月15日 下午6:37:11 19391 Exp $
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {
    @Pointcut("execution(public * com.fth.rbac.server.controller..*.*(..))")//切入点描述 这个是controller包的切入点
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        StringBuffer reqLog = new StringBuffer();

        List<Object> paramList = new ArrayList<>();
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof MultipartFile) {
                log.info("uri={},参数包含文件流,日志输出过滤", request.getRequestURI());
                continue;
            } else if (obj instanceof HttpServletResponse) {
                continue;
            } else if (obj instanceof HttpServletRequest) {
                continue;
            }
            paramList.add(obj);
        }
        String params = JSON.toJSONString(paramList);

        reqLog.append(request.getMethod()).append(" 【执行的方法】:")
                .append(request.getRequestURL().toString()).append(" 【参数为】").append(params);
        log.info("{}", reqLog);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 打印出来有点长，不打印了...
//        log.info("【返回值】= {} ", JSON.toJSONString(ret));
    }

}
