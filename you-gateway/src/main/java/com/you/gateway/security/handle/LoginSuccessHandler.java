package com.you.gateway.security.handle;

import com.you.common.core.utils.ServletUtils;
import com.you.gateway.security.model.SecurityUserDetails;
import com.you.gateway.security.service.TokenService;
import com.you.system.model.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 登录成功handler
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 15:09
 */
@Component
public class LoginSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final TokenService tokenService;

    public LoginSuccessHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(userDetails.getUsername());
        loginUser.setUserId(userDetails.getUserId());
        String token = tokenService.createToken(loginUser);
        return ServletUtils.webFluxResponseWriterOk(webFilterExchange.getExchange(), token, "登录成功");
    }
}
