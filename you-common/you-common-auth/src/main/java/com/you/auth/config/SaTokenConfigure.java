package com.you.auth.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.stp.StpLogic;
import com.alibaba.fastjson2.JSON;
import com.you.common.core.model.R;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * sa-token 权限认证配置类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-24 13:20
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    // Sa-Token 整合 jwt (Simple 简单模式)
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

    //注册sa-token全局过滤器
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")
                .addExclude("/favicon.ico")
                .setAuth(obj -> {
                    // 校验token身份凭证
                    SaSameUtil.checkCurrentRequestToken();
                })
                .setError(e -> {
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    return JSON.toJSONString(R.fail(e.getMessage()));
                });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }
}
