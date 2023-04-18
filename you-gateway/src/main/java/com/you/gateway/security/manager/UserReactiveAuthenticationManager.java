package com.you.gateway.security.manager;

import com.you.gateway.security.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

/**
 * 认证管理
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 16:03
 */
@Component
public class UserReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final UserDetailsServiceImpl userDetailsService;

    public UserReactiveAuthenticationManager(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // 获取表单提交的用户名和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        return userDetailsService.findByUsername(username).publishOn(Schedulers.parallel())
                // 过滤掉为null的
                .filter(Objects::nonNull)
                // 如果没有Mono里面元素了就说明没有查询到用户，抛出错误信息
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("用户名不存在:" + username))))
                // 校验用户信息, 并返回验证信息
                .map(userDetails -> {
                    // 验证密码是否正确
                    if (!new BCryptPasswordEncoder().matches(password, userDetails.getPassword())) {
                        Mono.defer(() -> Mono.error(new BadCredentialsException("密码不正确")));
                    }
                    // 用户状态判断
                    if (userDetails.isEnabled()) {
                        Mono.defer(() -> Mono.error(new DisabledException("账户被禁用")));
                    }
                    // 校验通过，组装验证信息
                    return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                });
    }
}
