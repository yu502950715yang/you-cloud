package com.you.gateway.security.service;

import com.you.common.core.constant.CacheConstants;
import com.you.common.core.constant.SecurityConstants;
import com.you.common.core.utils.JwtUtil;
import com.you.common.core.utils.uuid.IdUtils;
import com.you.common.redis.service.RedisService;
import com.you.system.model.LoginUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Token service
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 15:15
 */
@Service
public class TokenService {

    private final RedisService redisService;

    public TokenService(RedisService redisService) {
        this.redisService = redisService;
    }

    private final long expireTime = CacheConstants.EXPIRATION;
    private final long MILLIS_SECOND = 1000;
    private final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        refreshToken(loginUser);
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(SecurityConstants.USER_KEY, token);
        claims.put(SecurityConstants.DETAILS_USER_ID, loginUser.getUserId());
        claims.put(SecurityConstants.DETAILS_USERNAME, loginUser.getUsername());
        return JwtUtil.createToken(claims);
    }

    /**
     * 刷新redis中的令牌有效期
     *
     * @param loginUser 登录用户信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token) {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }
}
