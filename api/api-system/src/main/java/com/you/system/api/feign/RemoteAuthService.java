package com.you.system.api.feign;

import com.you.common.core.constant.ServiceNameConstants;
import com.you.common.core.domain.R;
import com.you.system.api.failback.RemoteAuthFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 权限服务接口
 */
@FeignClient(contextId = "remoteAuthService", value = ServiceNameConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteAuthFallbackFactory.class)
public interface RemoteAuthService {

    @GetMapping(value = "/auth/permission/{userId}")
    R<List<String>> getPermissions(@PathVariable("userId") Long userId);

    @GetMapping(value = "/auth/role/{userId}")
    R<List<String>> getRoles(@PathVariable("userId") Long userId);
}
