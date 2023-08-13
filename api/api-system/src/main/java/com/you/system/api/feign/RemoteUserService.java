package com.you.system.api.feign;

import com.you.common.core.constant.ServiceNameConstants;
import com.you.common.core.model.R;
import com.you.system.api.config.FeignConfig;
import com.you.system.api.failback.RemoteUserFallbackFactory;
import com.you.system.model.LoginUser;
import com.you.system.model.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户服务接口
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-12 17:11
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteUserFallbackFactory.class, configuration = FeignConfig.class)
public interface RemoteUserService {

    @GetMapping(value = "/user/{username}")
    R<SysUser> getUserByUsername(@PathVariable("username") String username);

    @GetMapping(value = "/user/getInfo")
    R<LoginUser> getInfo();
}
