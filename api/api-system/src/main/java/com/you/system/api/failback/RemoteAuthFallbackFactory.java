package com.you.system.api.failback;

import com.you.common.core.domain.R;
import com.you.system.api.feign.RemoteAuthService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RemoteUserService熔断降级
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 13:05
 */
@Component
public class RemoteAuthFallbackFactory implements FallbackFactory<RemoteAuthService> {
    @Override
    public RemoteAuthService create(Throwable cause) {
        return new RemoteAuthService() {

            @Override
            public R<List<String>> getPermissions(Long userId) {
                return R.fail("获取用户权限失败：" + cause.getMessage());
            }

            @Override
            public R<List<String>> getRoles(Long userId) {
                return R.fail("获取用户角色失败：" + cause.getMessage());
            }
        };
    }
}
