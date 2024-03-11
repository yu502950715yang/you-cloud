package com.you.system.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.you.common.excel.converters.StatusConverter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@HeadRowHeight(20)
public class SysDictTypeExcel {

    @ExcelProperty(value = "字典编号")
    private Long dictId;

    @ExcelProperty(value = "字典名称")
    @ColumnWidth(value = 15)
    private String dictName;

    @ExcelProperty(value = "字典类型")
    @ColumnWidth(value = 20)
    private String dictType;

    @ExcelProperty(value = "字典状态", converter = StatusConverter.class)
    private String status;

    @ExcelProperty(value = "备注")
    @ColumnWidth(value = 30)
    private String remark;

    @ExcelProperty(value = "创建时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(value = 25)
    private LocalDateTime createTime;
}
