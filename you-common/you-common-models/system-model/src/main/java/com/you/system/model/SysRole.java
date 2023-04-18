package com.you.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    @TableField("role_name")
    private String roleName;

    @TableField("type")
    private Integer type;

    @TableField("description")
    private String description;
}
