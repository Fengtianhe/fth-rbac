package com.fth.rbac.server.aop.token.impl;

import com.fth.rbac.server.aop.token.TokenManager;
import com.fth.rbac.server.aop.token.TokenModel;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.utils.redis.RedisConstants;
import com.fth.rbac.server.core.utils.redis.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author fengtianhe
 * @version $Id: TokenManagerImpl.java, v 0.1 2017年7月6日 上午11:19:14 fengtianhe Exp $
 */
@Component
@Slf4j
public class TokenManagerImpl implements TokenManager {

    public static final String TOKEN_FLAG = RedisConstants.TOKEN_FLAG;

    public static final Long TOKEN_EXPIRES_HOUR = (long) 60 * 24 * 30;
    @Autowired
    private RedisHelper redis;

    @Override
    public String createToken(String userId) {
        Date date = new Date();
        // 使用 uuid 作为源 token
        String token = date.getTime() + UUID.randomUUID().toString().replace("-", "");
        // 存储到 redis 并设置过期时间
        redis.set(TOKEN_FLAG + userId, token, 30, TimeUnit.MINUTES);
//        redis.hset(TOKEN_FLAG + userId, TOKEN_KEY, token);
//        redis.expire(TOKEN_FLAG + userId, TOKEN_EXPIRES_HOUR, TimeUnit.MINUTES);
        return date.getTime() + "_" + userId + "_" + token;
    }

    @Override
    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        //        String token = redis.get(TOKEN_FLAG + model.getUserId());
        String token = redis.get(TOKEN_FLAG + model.getUserId());
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        // 如果验证成功，说明此用户进行了一次有效操作，延长 token 的过期时间
        redis.expire(model.getUserId(), TOKEN_EXPIRES_HOUR, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public TokenModel getToken(String authentication) {
        log.info("获取token={}", authentication);
        String[] param = authentication.split("_");
        if (param.length != 3) {
            log.error("token格式不正确");
            throw new CommonException(ExceptionCodes.AUTHORIZATION_FAILURE);
        }
        // 使用 userId 和源 token 简单拼接成的 token，可以增加加密措施
        String userId = param[1];
        String token = param[2];
        return new TokenModel(userId, token);
    }

    @Override
    public void deleteToken(String userId) {
        redis.del(TOKEN_FLAG + userId);
    }

    @Override
    public void setSession(String userId, Map<String, String> infos) {
        if (StringUtils.isEmpty(userId)) {
            log.error("设置Session,userId不能为空");
            throw new CommonException(ExceptionCodes.AUTHORIZATION_FAILURE);
        }
        if (CollectionUtils.isEmpty(infos)) {
            log.error("设置Session时，内容数据不能为空");
            throw new CommonException(ExceptionCodes.AUTHORIZATION_FAILURE);
        }
        String key = TOKEN_FLAG + userId;
        if (redis.notExist(key)) {
            log.error("设置Session前userId必须存在");
            throw new CommonException(ExceptionCodes.AUTHORIZATION_FAILURE);
        }
        //过滤值为空的数据，否则会导致redis框架异常
        Iterator<Map.Entry<String, String>> it = infos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String value = entry.getValue();
            if (StringUtils.isEmpty(value)) {
                it.remove();
            }
        }
        if (infos.isEmpty()) {
            log.error("设置Session时没有可用的内容数据");
            throw new CommonException(ExceptionCodes.AUTHORIZATION_FAILURE);
        }
        redis.hMSet(key, infos);
    }

    @Override
    public Map<Object, Object> getSession(String userId) {
        return redis.hgetall(TOKEN_FLAG + userId);
    }

    @Override
    public boolean delFromSession(String userId, Object... fields) {
        if (StringUtils.isEmpty(userId)) {
            log.info("userId is blank");
        }
        if (fields == null) {
            log.info("fileds is null");
            return false;
        }

        if (!redis.exist(TOKEN_FLAG + userId)) {
            log.info("key is not exist, key={}", TOKEN_FLAG + userId);
            return false;
        }

        long result = redis.hdel(TOKEN_FLAG + userId, fields);
        log.info("已删除,rows={}", result);

        return true;
    }

}
