package com.you.common.log.enums;

import lombok.Getter;

@Getter
public enum OperLogTypenum {

    INSERT("新增"),
    UPDATE("修改"),
    DELETE("删除"),
    QUERY("查询"),
    DOWNLOAD("导出"),
    UPLOAD("导入"),
    CANCEL_AUTH("取消授权"),
    AUTH("授权"),
    ENABLE_DISABLE("启用禁用"),
    RESET_PWD("重置密码"),
    OTHER("其他"),
    ;

    private final String msg;

    OperLogTypenum(String msg) {
        this.msg = msg;
    }

    /**
     * 不记录响应数据
     * @param type
     * @return
     */
    public static boolean noRecordResponse(OperLogTypenum type) {
        return type == QUERY || type == DOWNLOAD;
    }
}
