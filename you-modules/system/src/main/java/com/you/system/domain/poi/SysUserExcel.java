package com.you.system.domain.poi;

import com.alibaba.excel.annotation.ExcelProperty;
import com.you.common.core.excel.converters.StatusConverter;
import lombok.Data;

@Data
public class SysUserExcel {

    @ExcelProperty(value = "用户编号")
    private String userId;

    @ExcelProperty(value = "用户名称")
    private String username;

    @ExcelProperty(value = "用户昵称")
    private String nickname;

    @ExcelProperty(value = "部门")
    private String deptName;

    @ExcelProperty(value = "手机号")
    private String phone;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty(value = "状态", converter = StatusConverter.class)
    private String status;


}
