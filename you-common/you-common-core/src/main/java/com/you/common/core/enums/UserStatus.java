package com.you.common.core.enums;

import lombok.Getter;

/**
 * 用户状态
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-20 15:40
 */
@Getter
public enum UserStatus {
    OK(0, "正常"),
    DISABLE(1, "禁用");

    private final Integer code;
    private final String info;

    UserStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

}
