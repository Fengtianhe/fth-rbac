package com.fth.rbac.server.aop;

import com.alibaba.fastjson.JSON;
import com.fth.rbac.server.core.entity.FrApp;
import com.fth.rbac.server.core.entity.FrResource;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.service.AppService;
import com.fth.rbac.server.service.ResourceService;
import javafx.util.Pair;
import jdk.internal.dynalink.ChainedCallSite;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/7
 * content: 解决NPM包SDK请求的跨域问题，利用appId 和 域名判断是否是服务器
 */
@Component
@Slf4j
public class CorsFilter implements Filter {
    @Autowired
    private AppService appService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            // 只处理/sdk/**请求
            String requestURI = request.getRequestURI();
            int i = requestURI.indexOf("/sdk");
            if (i != 0) {
                log.info("请求路径为{}， 通过CorsFilter校验", requestURI);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            // 首先判断来源是否是npm包的方式请求的
            Map<String, String> params = this.params(request);
            String client = params.get("client");
            if (!"npm".equalsIgnoreCase(client)) {
                log.info("请求路径为{}, 请求客户端类型为{}， 通过CorsFilter校验", requestURI, client);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            // 判断appId 和 domain 是否匹配，并且是否在
            String appId = params.get("appId");
            if (StringUtils.isEmpty(appId)) {
                throw new CommonException(ExceptionCodes.SDK_UNKNOWN_APPID);
            }
            FrApp app = appService.selectByAppId(appId);
            if (app == null) {
                throw new CommonException(ExceptionCodes.SDK_UNKNOWN_APP);
            }
            String remoteHost = request.getRemoteHost();
            if (!app.getDomain().equals(remoteHost)) {

            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (CommonException e) {
            this.returnJson(request, response, e.getCode(), e.getMessage());
            return;
        }
    }

    @Override
    public void destroy() {

    }

    private Map<String, String> params(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return parameterMap.entrySet().stream()
                .map(it -> new Pair<>(it.getKey(),
                        Optional.ofNullable(it.getValue())
                                .map(value -> value.length > 0 ? value[0] : null)
                                .orElse(null)))
                .filter(it -> !StringUtils.isEmpty(it.getValue()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private void returnJson(HttpServletRequest request, HttpServletResponse response, int code, String message) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);

        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
//        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
        try {
            if ("OPTIONS".equals(request.getMethod())) {
                response.getWriter().println("ok");
                return;
            }

            writer = response.getWriter();
            writer.print(JSON.toJSONString(map));
        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
