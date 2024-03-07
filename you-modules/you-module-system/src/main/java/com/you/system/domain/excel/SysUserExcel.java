package com.you.system.domain.excel;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.you.common.core.constant.DictTypeConstants;
import com.you.common.excel.annotation.ExcelDictItem;
import com.you.common.excel.converters.ExcelDictConverter;
import com.you.common.excel.converters.StatusConverter;
import lombok.Data;

import java.util.List;

@Data
@HeadRowHeight(20)
public class SysUserExcel {

    @ExcelProperty(value = "用户编号")
    private String userId;

    @ExcelProperty(value = "用户名称")
    private String username;

    @ExcelProperty(value = "用户昵称")
    private String nickname;

    @ExcelProperty(value = "性别", converter = ExcelDictConverter.class)
    @ExcelDictItem(type = DictTypeConstants.SYS_USER_SEX)
    private String sex;

    @ExcelProperty(value = "部门")
    @ColumnWidth(value = 30)
    private String deptNamesStr;

    @ExcelProperty(value = "手机号")
    private String phone;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty(value = "状态", converter = StatusConverter.class)
    private String status;

    @ExcelIgnore
    private Long deptId;

    /**
     * 部门的所有上级
     */
    @ExcelIgnore
    private String deptAncestors;

    /**
     * 上级+本部门id
     */
    @ExcelIgnore
    private List<String> deptIds;

    /**
     * 所有部门名称
     */
    @ExcelIgnore
    private List<String> deptNames;

    public String getDeptNamesStr() {
        if (CollUtil.isEmpty(deptNames)) {
            return "";
        }
        return String.join("/", deptNames);
    }
}
