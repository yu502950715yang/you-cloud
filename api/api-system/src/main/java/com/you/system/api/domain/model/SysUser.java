package com.you.system.api.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.you.validation.ValidationGroups;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class SysUser {

    @NotNull(message = "用户id不能为空", groups = ValidationGroups.Update.class)
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @NotNull(message = "部门id不能为空", groups = ValidationGroups.Common.class)
    @TableField("dept_id")
    private Long deptId;

    @NotNull(message = "用户名称不能为空", groups = ValidationGroups.Add.class)
    @Length(min = 2, max = 20, message = "用户名称长度必须介于2到20之间", groups = ValidationGroups.Common.class)
    @TableField("username")
    private String username;

    @NotNull(message = "用户昵称不能为空", groups = ValidationGroups.Common.class)
    @Length(min = 1, max = 20, message = "用户昵称长度必须介于1到20之间", groups = ValidationGroups.Common.class)
    @TableField("nickname")
    private String nickname;

    @TableField("user_type")
    private String userType;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("sex")
    private String sex;

    @NotNull(message = "密码不能为空", groups = ValidationGroups.Add.class)
    @TableField("password")
    private String password;

    /**
     * 用户状态 0正常 1停用
     */
    @NotNull(message = "用户状态不能为空", groups = ValidationGroups.Common.class)
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

    @TableField("remark")
    private String remark;
}
