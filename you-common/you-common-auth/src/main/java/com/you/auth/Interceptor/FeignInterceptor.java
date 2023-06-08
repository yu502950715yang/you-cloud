package com.you.auth.Interceptor;

import cn.dev33.satoken.same.SaSameUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * feign拦截器
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-25 10:19
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    // 为 Feign 的 RCP调用 添加请求头Same-Token
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken());
    }
}
