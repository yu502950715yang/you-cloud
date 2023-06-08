package com.you.common.core.enums;

/**
 * 用户状态
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-20 15:40
 */
public enum UserStatus {
    OK(1, "正常"),
    DISABLE(0, "禁用");

    private final Integer code;
    private final String info;

    UserStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
