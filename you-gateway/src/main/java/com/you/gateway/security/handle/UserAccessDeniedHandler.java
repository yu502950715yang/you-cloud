package com.you.gateway.security.handle;

import com.you.common.core.constant.HttpStatus;
import com.you.common.core.utils.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 暂无权限处理
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-18 11:27
 */
public class UserAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return ServletUtils.webFluxResponseWriter(exchange, "未授权", HttpStatus.FORBIDDEN);
    }
}
