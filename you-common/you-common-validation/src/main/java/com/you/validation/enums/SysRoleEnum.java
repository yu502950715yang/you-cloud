package com.you.validation.enums;

import lombok.Getter;

/**
 * 角色状态枚举
 */
@Getter
public enum SysRoleEnum {

    NORMAL("0", "正常"),
    DISABLE("1", "停用");

    private final String code;
    private final String desc;

    SysRoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
