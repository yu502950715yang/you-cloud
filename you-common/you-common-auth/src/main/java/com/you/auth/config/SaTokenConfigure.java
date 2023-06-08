package com.you.auth.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.same.SaSameUtil;
import com.alibaba.fastjson2.JSON;
import com.you.common.core.model.R;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sa-token 权限认证配置类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-24 13:20
 */
@Configuration
public class SaTokenConfigure {

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
}
