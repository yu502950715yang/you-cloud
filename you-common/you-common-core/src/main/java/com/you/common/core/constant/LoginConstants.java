package com.you.common.core.constant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 登录常量
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-24 10:12
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginConstants {

    /**
     * 登录设备 pc
     */
    public static final String DEVICE_PC = "PC";

    /**
     * 登录信息存储用户名
     */
    public static final String EXTRA_USERNAME = "username";
}
