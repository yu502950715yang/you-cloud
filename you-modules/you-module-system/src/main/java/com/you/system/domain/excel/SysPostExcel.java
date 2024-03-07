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
public class SysPostExcel {

    @ExcelProperty(value = "岗位ID")
    private Long postId;

    /**
     * 岗位编码
     */
    @ExcelProperty(value = "岗位编码")
    private String postCode;

    /**
     * 岗位名称
     */
    @ExcelProperty(value = "名称")
    private String postName;

    /**
     * 显示顺序
     */
    @ExcelProperty(value = "显示顺序")
    private Integer postSort;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态", converter = StatusConverter.class)
    private String status;

    /**
     * 创建时间
     */
    @ColumnWidth(value = 25)
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
}
