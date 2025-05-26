package com.you.common.log.enums;

import lombok.Getter;

/**
 * 操作类别枚举
 */
@Getter
public enum OperatorTypeEnum {

    // 其他
    OTHER(0),
    // PC
    WEB(1),
    // 移动端
    PHONE(2);

    private final int code;

    OperatorTypeEnum(int code) {
        this.code = code;
    }
}
