package com.you.gateway.saToken;

import cn.dev33.satoken.stp.StpInterface;
import com.you.common.core.constant.CacheConstants;
import com.you.common.core.model.R;
import com.you.common.core.utils.text.Convert;
import com.you.common.redis.service.RedisService;
import com.you.system.api.feign.RemoteUserService;
import com.you.system.model.LoginUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private final RemoteUserService remoteUserService;

    public StpInterfaceImpl(RedisService redisService, RemoteUserService remoteUserService) {
        this.redisService = redisService;
        this.remoteUserService = remoteUserService;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = new ArrayList<>();
        String username = String.valueOf(loginId);
        String redisKey = redisService.splitRedisKey(CacheConstants.REDIS_USER_PERMISSION_KEY, username);
        if (redisService.hasKey(redisKey)) {
            Object redisList = redisService.getCacheObject(redisKey);
            permissionList = Convert.objectToList(redisList, String.class);
        } else {
            // 如果不存在，则先查询再存入缓存中
            R<LoginUser> result = remoteUserService.getUserByUsername(username);
            if (null != result && null != result.getData()) {
                permissionList = result.getData().getPermissions();
                // 存入redis
                redisService.setCacheObject(redisKey, permissionList);
            }
        }
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
