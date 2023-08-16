package com.you.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.you.validation.ValidationGroups;
import com.you.validation.annotation.RoleStatusValid;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 角色
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 13:49
 */
@Data
@TableName("sys_role")
public class SysRole {

    @NotNull(message = "角色id不能为空", groups = ValidationGroups.Other.class)
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    @TableField("role_name")
    private String roleName;

    @TableField("role_key")
    private String roleKey;

    @TableField("role_sort")
    private Integer roleSort;

    @TableField("data_scope")
    private Integer dataScope;

    @TableField("menu_check_strictly")
    private Integer menuCheckStrictly;

    @TableField("dept_check_strictly")
    private Integer deptCheckStrictly;

    @RoleStatusValid(groups = ValidationGroups.Other.class)
    @TableField("status")
    private String status;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private String updateTime;

    @TableField("remark")
    private String remark;
}
