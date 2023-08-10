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

    private String username;
    private String password;
    private String verifyCode;
    private String verifyCodeKey;
}
