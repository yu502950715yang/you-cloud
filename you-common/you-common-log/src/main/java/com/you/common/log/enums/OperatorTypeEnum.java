package com.you.common.log.enums;

import lombok.Getter;

/**
 * 操作类别枚举
 */
@Getter
public enum OperatorTypeEnum {

    OTHER(0),
    WEB(1),
    PHONE(2);

    private final int code;

    OperatorTypeEnum(int code) {
        this.code = code;
    }
}
