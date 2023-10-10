package com.you.gateway.filter;

import cn.hutool.http.HtmlUtil;
import com.you.common.core.utils.StrUtils;
import com.you.gateway.config.properties.XssProperties;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Xss过滤器
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-10-09 13:44
 **/
@Component
public class XssFilter implements GlobalFilter, Ordered {

    /**
     * xss配置文件
     */
    private final XssProperties xssProperties;

    public XssFilter(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // GET DELETE 不过滤
        HttpMethod method = request.getMethod();
        if (method == null || method == HttpMethod.GET || method == HttpMethod.DELETE) {
            return chain.filter(exchange);
        }
        // 白名单url 不过滤
        URI uri = request.getURI();
        String url = uri.getPath();
        if (StrUtils.matches(url, xssProperties.getExcludeUrls())) {
            return chain.filter(exchange);
        }
        return DataBufferUtils.join(request.getBody()).flatMap(d -> Mono.just(Optional.of(d))).defaultIfEmpty(Optional.empty()).flatMap(optional -> {
            // 取出body中的参数
            String bodyString = "";
            if (optional.isPresent()) {
                byte[] oldBytes = new byte[optional.get().readableByteCount()];
                optional.get().read(oldBytes);
                bodyString = new String(oldBytes, StandardCharsets.UTF_8);
            }
            HttpHeaders httpHeaders = request.getHeaders();
            // 执行XSS清理
            bodyString = HtmlUtil.cleanHtmlTag(bodyString);

            ServerHttpRequest newRequest = request.mutate().uri(uri).build();

            // 重新构造body
            byte[] newBytes = bodyString.getBytes(StandardCharsets.UTF_8);
            DataBuffer bodyDataBuffer = toDataBuffer(newBytes);
            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

            // 重新构造header
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(httpHeaders);
            // 由于修改了传递参数，需要重新设置CONTENT_LENGTH，长度是字节长度，不是字符串长度
            int length = newBytes.length;
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            headers.setContentLength(length);
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf8");
            // 重写ServerHttpRequestDecorator，修改了body和header，重写getBody和getHeaders方法
            newRequest = new ServerHttpRequestDecorator(newRequest) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return headers;
                }
            };
            return chain.filter(exchange.mutate().request(newRequest).build());
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 字节数组转DataBuffer
     *
     * @param bytes 字节数组
     * @return DataBuffer
     */
    private DataBuffer toDataBuffer(byte[] bytes) {
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }
}
