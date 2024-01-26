package com.you.auth.domain.model;

import com.you.validation.ValidationGroups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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
    @NotNull(message = "用户名/密码必须填写", groups = ValidationGroups.Common.class)
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "用户名/密码必须填写", groups = ValidationGroups.Common.class)
    private String password;

    /**
     * 验证码
     */
    @NotNull(message = "验证码必须填写", groups = ValidationGroups.Common.class)
    private String verifyCode;

    /**
     * 验证码key值
     */
    @NotNull(message = "验证码key必须填写", groups = ValidationGroups.Common.class)
    private String verifyCodeKey;
}
