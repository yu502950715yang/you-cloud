package com.you.filter;

import com.you.common.core.constant.HttpStatus;
import com.you.common.core.constant.TokenConstants;
import com.you.common.core.utils.StrUtils;
import com.you.config.properties.IgnoreWhiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 鉴权
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-06 13:48
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder builder = request.mutate();

        String url = request.getURI().getPath();
        // 跳过不需要验证的url
        if (StrUtils.matches(url, ignoreWhite.getWhiteList())) {
            return chain.filter(exchange);
        }
        String token = getToken(request);
        if (StrUtils.isEmpty(token)) {
        }
        return null;
    }

    @Override
    public int getOrder() {
        return -200;
    }

    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        // 如果前端设置了令牌前缀，则删除前缀
        if (StrUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }
}
