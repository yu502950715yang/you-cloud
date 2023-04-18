package com.you.gateway.security.model;

import com.you.system.model.LoginUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义用户
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 13:32
 */
@Getter
@Setter
public class SecurityUserDetails implements UserDetails {

    private String userId;
    private String username;
    private String password;
    /**
     * 用户状态 0禁用 1启用
     */
    private Integer status;

    private List<String> permissions;

    /**
     * 权限列表
     */
    Collection<? extends GrantedAuthority> authorities;

    public SecurityUserDetails() {
    }

    public SecurityUserDetails(LoginUser loginUser) {
        this.userId = loginUser.getUserId();
        this.username = loginUser.getUsername();
        this.password = loginUser.getPassword();
        this.status = loginUser.getStatus();
        this.permissions = loginUser.getPermissions();
        if (null != loginUser.getPermissions() && loginUser.getPermissions().size() > 0) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            loginUser.getPermissions().forEach(s -> authorities.add(new SimpleGrantedAuthority(s)));
            this.authorities = authorities;
        }
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status != 0;
    }
}
