package com.you.system.domain.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.you.common.core.excel.converters.StatusConverter;
import com.you.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@TableName("sys_post")
public class SysPost {

    @ExcelProperty(value = "岗位ID")
    @TableId(value = "post_id", type = IdType.AUTO)
    @NotNull(message = "岗位ID不能为空", groups = ValidationGroups.Update.class)
    private Long postId;

    /**
     * 岗位编码
     */
    @ExcelProperty(value = "岗位编码")
    @NotNull(message = "岗位编码不能为空", groups = ValidationGroups.Common.class)
    @TableField("post_code")
    private String postCode;

    /**
     * 岗位名称
     */
    @ExcelProperty(value = "名称")
    @NotNull(message = "岗位名称不能为空", groups = ValidationGroups.Common.class)
    @TableField("post_name")
    private String postName;

    /**
     * 显示顺序
     */
    @ExcelProperty(value = "显示顺序")
    @NotNull(message = "显示顺序不能为空", groups = ValidationGroups.Common.class)
    @TableField("post_sort")
    private Integer postSort;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态", converter = StatusConverter.class)
    @NotNull(message = "状态不能为空", groups = ValidationGroups.Common.class)
    @TableField("status")
    private String status;

    /**
     * 创建者
     */
    @ExcelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @ColumnWidth(value = 25)
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @ExcelProperty(value = "更新人")
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @ColumnWidth(value = 25)
    @ExcelProperty(value = "更新时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @TableField("remark")
    private String remark;
}