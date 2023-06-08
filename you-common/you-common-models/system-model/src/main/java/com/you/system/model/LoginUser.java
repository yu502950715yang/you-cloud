package com.you.system.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录用户实体
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 13:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class LoginUser extends SysUser {

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 权限list
     */
    private List<String> permissions;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;
}
