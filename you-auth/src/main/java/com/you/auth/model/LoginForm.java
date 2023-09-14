package com.you.auth.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录model
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-21 09:47
 */
@Getter
@Setter
public class LoginForm {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 验证码key值
     */
    private String verifyCodeKey;
}
