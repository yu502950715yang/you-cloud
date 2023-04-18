package com.you.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_user")
public class SysUser {

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private String userId;

    @TableField("username")
    private String username;

    @TableField("real_name")
    private String realName;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    /**
     * 用户状态 0禁用 1启用
     */
    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private Date createTime;
}
