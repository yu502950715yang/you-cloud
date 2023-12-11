package com.you.common.core.enums;

import lombok.Getter;

/**
 * 通用删除标记枚举
 */
@Getter
public enum DelFlagEnum {

    /**
     * 正常
     */
    NORMAL(0, "正常"),

    /**
     * 删除
     */
    DELETE(2, "删除");

    private final int code;

    private final String message;

    DelFlagEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
