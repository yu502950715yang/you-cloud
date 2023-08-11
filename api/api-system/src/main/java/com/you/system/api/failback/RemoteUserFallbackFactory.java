package com.you.system.api.failback;

import com.you.common.core.model.R;
import com.you.system.api.feign.RemoteUserService;
import com.you.system.model.LoginUser;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * RemoteUserService熔断降级
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 13:05
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable cause) {
        return new RemoteUserService() {
            @Override
            public R<LoginUser> getUserByUsername(String username) {
                return R.fail("获取用户失败：" + cause.getMessage());
            }

            @Override
            public R<LoginUser> getInfo() {
                return R.fail("获取用户失败：" + cause.getMessage());
            }
        };
    }
}
