package com.you.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.you.validation.ValidationGroups;
import com.you.validation.annotation.RoleStatusValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
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
    @NotNull(message = "角色id不能为空", groups = ValidationGroups.Update.class)
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    @NotNull(message = "角色名称不能为空", groups = ValidationGroups.Common.class)
    @Length(max = 20, message = "角色名称最多20个字符", groups = ValidationGroups.Common.class)
    @TableField("role_name")
    private String roleName;

    @NotNull(message = "权限字符串不能为空", groups = ValidationGroups.Common.class)
    @Length(max = 50, message = "权限字符串最多50个字符", groups = ValidationGroups.Common.class)
    @TableField("role_key")
    private String roleKey;

    @NotNull(message = "角色顺序不能为空", groups = ValidationGroups.Common.class)
    @Max(value = 9999, message = "角色顺序最大为9999", groups = ValidationGroups.Common.class)
    @TableField("role_sort")
    private Integer roleSort;

    @TableField("data_scope")
    private Integer dataScope;

    @TableField("menu_check_strictly")
    private boolean menuCheckStrictly;

    @TableField("dept_check_strictly")
    private boolean deptCheckStrictly;

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
    private LocalDateTime updateTime;

    @Length(max = 500, message = "备注最多500个字符", groups = ValidationGroups.Common.class)
    @TableField("remark")
    private String remark;
}
