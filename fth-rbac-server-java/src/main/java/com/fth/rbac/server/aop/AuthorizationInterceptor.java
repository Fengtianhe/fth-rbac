package com.fth.rbac.server.aop;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fth.rbac.server.aop.token.TokenManager;
import com.fth.rbac.server.aop.token.TokenModel;
import com.fth.rbac.server.core.entity.FrSysUser;
import com.fth.rbac.server.core.enums.SysUserStatusEnum;
import com.fth.rbac.server.core.enums.base.EnumFactory;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.core.utils.common.ResponseConstant;
import com.fth.rbac.server.core.utils.date.DatePattern;
import com.fth.rbac.server.service.SysUserService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
@Component
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String[] urlWhiteList = {"/v2/api-docs", "/error", "/swagger-ui.html", "/admin", "swagger", "/static/"};
        String[] contentTypeWhiteList = {"text/html;charset=utf-8"};

        // 过滤文档类型，html
        if (Arrays.asList(contentTypeWhiteList).contains(request.getContentType())) {
            return true;
        }

        // 过滤白名单的请求地址
        String requestURI = request.getRequestURI();
        List<String> whiteList = Arrays.asList(urlWhiteList);
        for (String whiteItem : whiteList) {
            if (requestURI.contains(whiteItem)) {
                return true;
            }
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 检查token以及角色访问页面权限
        try {
            verifyToken(request);
        } catch (Exception e) {
            sendJsonMessage(response, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }

    /**
     * 验证token
     *
     * @param request
     */
    private String verifyToken(HttpServletRequest request) {
        String authorization = request.getHeader("X-Authorization-Token");
        if (StringUtils.isBlank(authorization)) {
            log.error("token is blank");
            throw new CommonException(ExceptionCodes.AUTHORIZATION_FAILURE);
        }

        // 验证 token
        TokenModel model = tokenManager.getToken(authorization);
        if (!tokenManager.checkToken(model)) {
            log.debug("token校验失败");
            throw new CommonException(ExceptionCodes.AUTHORIZATION_FAILURE);
        }
        // 如果 token 验证成功，将 token 对应的用户 id 存在 request 中，便于之后注入
        request.setAttribute("userId", model.getUserId());

        log.debug("token校验完成");
        return model.getUserId();
    }

    /**
     * 将某个对象转换成json格式并发送到客户端
     *
     * @param response
     * @param message
     * @throws Exception
     */
    private void sendJsonMessage(HttpServletResponse response, String message) throws Exception {
        CommonResponse obj = new CommonResponse();
        obj.setCode(401);
        obj.setMessage(message);

        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        writer.close();
        response.flushBuffer();
    }
}

