package com.you.system.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.you.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@TableName("sys_dept")
public class SysDept {

    @NotNull(message = "部门ID不能为空", groups = ValidationGroups.Update.class)
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;

    @NotNull(message = "父级部门不能为空", groups = ValidationGroups.Common.class)
    @TableField("parent_id")
    private Long parentId;

    /**
     * 祖籍列表
     */
    @TableField("ancestors")
    private String ancestors;

    @NotNull(message = "部门名称不能为空", groups = ValidationGroups.Common.class)
    @TableField("dept_name")
    private String deptName;

    @NotNull(message = "部门排序不能为空", groups = ValidationGroups.Common.class)
    @TableField("order_num")
    private Integer orderNum;

    @TableField("leader")
    private String leader;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    @TableField("status")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableField("del_flag")
    private String delFlag;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
