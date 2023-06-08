package com.you.gateway.saToken.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.you.common.core.model.R;
import com.you.gateway.config.properties.IgnoreWhiteProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sa全局配置类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-18 14:43
 */
@Configuration
public class SaTokenConfigure {

    private final IgnoreWhiteProperties ignoreWhite;

    public SaTokenConfigure(IgnoreWhiteProperties ignoreWhite) {
        this.ignoreWhite = ignoreWhite;
    }

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 指定 [拦截路由]
                .addInclude("/**")
                // 指定 [放行路由]
                .addExclude("/favicon.ico")
                // 白名单放行
                .addExclude(ignoreWhite.getWhiteList().toArray(String[]::new))
                // 指定[认证函数]: 每次请求执行
                .setAuth(obj -> {
                    // 登录认证：除登录接口都需要认证
                    SaRouter.match("/**", "/auth/login", StpUtil::checkLogin);
                })
                // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数
                .setError(e -> {
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    return JSON.toJSONString(R.fail(e.getMessage()));
                });
    }
}
