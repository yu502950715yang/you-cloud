package com.you.auth.service;

import cn.dev33.satoken.stp.StpInterface;
import com.you.common.core.constant.CacheConstants;
import com.you.common.core.utils.text.Convert;
import com.you.common.redis.service.RedisService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-20 15:59
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    private final RedisService redisService;
    private final AuthService authService;

    public StpInterfaceImpl(RedisService redisService, AuthService authService) {
        this.redisService = redisService;
        this.authService = authService;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList;
        String userId = String.valueOf(loginId);
        String redisKey = redisService.splitRedisKey(CacheConstants.REDIS_USER_PERMISSION_KEY, userId);
        if (redisService.hasKey(redisKey)) {
            Object redisList = redisService.getCacheObject(redisKey);
            permissionList = Convert.objectToList(redisList, String.class);
        } else {
            // 如果不存在，则先查询再存入缓存中
            permissionList = authService.getPermissions(Long.valueOf(userId));
            // 存入redis
            redisService.setCacheObject(redisKey, permissionList);
        }
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roles;
        String userId = String.valueOf(loginId);
        String redisKey = redisService.splitRedisKey(CacheConstants.REDIS_USER_ROLE_KEY, userId);
        if (redisService.hasKey(redisKey)) {
            Object redisList = redisService.getCacheObject(redisKey);
            roles = Convert.objectToList(redisList, String.class);
        } else {
            // 如果不存在，则先查询再存入缓存中
            roles = authService.getRoles(userId);
            // 存入redis
            redisService.setCacheObject(redisKey, roles);
        }
        return roles;
    }
}
