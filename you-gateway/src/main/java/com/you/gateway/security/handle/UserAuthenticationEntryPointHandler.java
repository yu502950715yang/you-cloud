package com.you.gateway.security.handle;

import com.you.common.core.constant.HttpStatus;
import com.you.common.core.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 未登录处理
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 17:52
 */
@Component
public class UserAuthenticationEntryPointHandler implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return ServletUtils.webFluxResponseWriter(exchange, "请先登录", HttpStatus.UNAUTHORIZED);
    }
}
