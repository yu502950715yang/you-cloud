package com.you.gateway.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.you.common.core.constant.HttpStatus;
import com.you.common.core.domain.R;
import com.you.gateway.config.properties.IgnoreWhiteProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * sa全局配置类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-18 14:43
 */
@Configuration
@Slf4j
public class SaTokenConfigure {

    private final IgnoreWhiteProperties ignoreWhite;

    public SaTokenConfigure(IgnoreWhiteProperties ignoreWhite) {
        this.ignoreWhite = ignoreWhite;
    }

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        List<String> whiteList = ignoreWhite.getWhiteList();
        String[] whiteArray = whiteList.toArray(new String[0]);
        return new SaReactorFilter()
                // 指定 [拦截路由]
                .addInclude("/**")
                // 指定 [放行路由]
                .addExclude("/favicon.ico")
                // 白名单放行
                .addExclude(whiteArray)
                // 指定[认证函数]: 每次请求执行
                .setAuth(obj -> {
                    // 登录认证：除登录接口都需要认证
                    SaRouter.match("/**", "/auth/login", StpUtil::checkLogin);
                })
                // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数
                .setError(e -> {
                    log.error("sa-token认证异常：{}", e.getMessage());
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    if (e.getClass().equals(NotLoginException.class)) {
                        return JSON.toJSONString(R.fail(HttpStatus.UNAUTHORIZED, e.getMessage()));
                    }
                    return JSON.toJSONString(R.fail(e.getMessage()));
                });
    }
}
