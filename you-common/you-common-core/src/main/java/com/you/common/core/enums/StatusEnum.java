package com.you.common.core.enums;

import lombok.Getter;

/**
 * 通用状态枚举
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-20 15:40
 */
@Getter
public enum StatusEnum {
    OK("0", "正常"),
    DISABLE("1", "禁用");

    private final String code;
    private final String info;

    StatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
