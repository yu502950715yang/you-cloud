package com.you.gateway.security.service;

import com.you.common.core.model.R;
import com.you.gateway.security.model.SecurityUserDetails;
import com.you.system.api.feign.RemoteUserService;
import com.you.system.model.LoginUser;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Description on this file, you will change here.
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-11 14:51
 */
@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final RemoteUserService userService;

    public UserDetailsServiceImpl(RemoteUserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        // 从数据库中获取对应用户名的用户数据
        R<LoginUser> sysUserResult = userService.getUserByUsername(username);
        if (null == sysUserResult || null == sysUserResult.getData()) {
            return Mono.error(new UsernameNotFoundException("用户名不存在"));
        }
        LoginUser loginUser = sysUserResult.getData();
        SecurityUserDetails user = new SecurityUserDetails(loginUser);
//        UserDetails user = User.withUsername(loginUser.getUsername())
//                .password(loginUser.getPassword())
//
//                // 权限
//                .authorities(authorities)
//                // 账户是否被禁用
//                .disabled(loginUser.getStatus() == 0)
//                .build();
        return Mono.just(user);
    }
}
