package com.you.gateway.security.handle;

import com.you.common.core.constant.HttpStatus;
import com.you.common.core.utils.ServletUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 登录失败 handler
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 17:45
 */
@Component
public class LoginFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        if (exception instanceof UsernameNotFoundException) {
            return ServletUtils.webFluxResponseWriter(webFilterExchange.getExchange(), "用户名不存在", HttpStatus.ERROR);
        } else if (exception instanceof LockedException) {
            return ServletUtils.webFluxResponseWriter(webFilterExchange.getExchange(), "用户被冻结", HttpStatus.ERROR);
        } else if (exception instanceof BadCredentialsException) {
            return ServletUtils.webFluxResponseWriter(webFilterExchange.getExchange(), "用户名密码不正确", HttpStatus.ERROR);
        } else {
            return ServletUtils.webFluxResponseWriter(webFilterExchange.getExchange(), "登录失败", HttpStatus.ERROR);
        }
    }
}
