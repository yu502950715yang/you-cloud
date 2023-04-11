package com.you.gateway.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedList;

/**
 * Webflux Security核心配置类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-11 14:13
 */
//开启security配置
@EnableWebSecurity
public class WebfluxSecurityConfig {

    /**
     * 排除鉴权的url
     */
    private final String[] excludes = {"/auth/login", "/auth/logout"};

    /**
     * 认证信息管理器
     */
    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager() {
        LinkedList<ReactiveAuthenticationManager> authenticationManagerList = new LinkedList<>();
        /*authenticationManagerList.add(authentication -> {
            // 其他登陆方式 (比如手机号验证码登陆) 可在此设置不得抛出异常或者 Mono.error
            return Mono.empty();
        });*/
        // 必须放最后不然会优先使用用户名密码校验但是用户名密码不对时此 AuthenticationManager 会调用 Mono.error 造成后面的 AuthenticationManager 不生效
//        authenticationManagerList.add(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsServiceImpl));
//        authenticationManagerList.add(tokenAuthenticationManager);
        return new DelegatingReactiveAuthenticationManager(authenticationManagerList);
    }

    /**
     * BCrypt密码编码
     */
    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
//        httpSecurity.authenticationManager()
//    }

}
