package com.you.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class SysUser {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @TableField("dept_id")
    private Long deptId;

    @TableField("username")
    private String username;

    @TableField("nickname")
    private String nickname;

    @TableField("user_type")
    private String userType;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("password")
    private String password;

    /**
     * 用户状态 0正常 1停用
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

    @TableField("remark")
    private String remark;
}
