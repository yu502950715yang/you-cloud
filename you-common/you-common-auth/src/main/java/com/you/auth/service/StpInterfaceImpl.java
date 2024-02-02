package com.you.auth.service;

import cn.dev33.satoken.stp.StpInterface;
import com.you.common.core.constant.CacheConstants;
import com.you.common.core.domain.R;
import com.you.common.core.utils.text.Convert;
import com.you.common.redis.service.RedisService;
import com.you.system.api.feign.RemoteAuthService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-20 15:59
 */
@Service
public class StpInterfaceImpl implements StpInterface {

    private final RedisService redisService;
    private final RemoteAuthService remoteAuthService;

    public StpInterfaceImpl(RedisService redisService, RemoteAuthService remoteAuthService) {
        this.redisService = redisService;
        this.remoteAuthService = remoteAuthService;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = new ArrayList<>();
        String userId = String.valueOf(loginId);
        String redisKey = redisService.splitRedisKey(CacheConstants.REDIS_USER_PERMISSION_KEY, userId);
        if (redisService.hasKey(redisKey)) {
            Object redisList = redisService.getCacheObject(redisKey);
            permissionList = Convert.objectToList(redisList, String.class);
        } else {
            // 如果不存在，则先查询再存入缓存中
            R<List<String>> result = remoteAuthService.getPermissions(Long.valueOf(userId));
            if (Boolean.TRUE.equals(R.isSuccess(result))) {
                permissionList = result.getData();
                // 存入redis
                redisService.setCacheObject(redisKey, permissionList);
            }
        }
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roles = new ArrayList<>();
        String userId = String.valueOf(loginId);
        String redisKey = redisService.splitRedisKey(CacheConstants.REDIS_USER_ROLE_KEY, userId);
        if (redisService.hasKey(redisKey)) {
            Object redisList = redisService.getCacheObject(redisKey);
            roles = Convert.objectToList(redisList, String.class);
        } else {
            // 如果不存在，则先查询再存入缓存中
            R<List<String>> result = remoteAuthService.getRoles(Long.valueOf(userId));
            if (Boolean.TRUE.equals(R.isSuccess(result))) {
                roles = result.getData();
                // 存入redis
                redisService.setCacheObject(redisKey, roles);
            }
        }
        return roles;
    }
}
