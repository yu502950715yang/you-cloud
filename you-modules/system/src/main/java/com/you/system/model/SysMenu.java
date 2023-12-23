package com.you.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.you.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@TableName("sys_menu")
public class SysMenu {

    /**
     * 菜单ID
     */
    @NotNull(message = "菜单ID不能为空", groups = ValidationGroups.Update.class)
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     */
    @NotNull(message = "菜单名称不能为空", groups = ValidationGroups.Common.class)
    @Size(max = 50, message = "菜单名称长度不能超过50个字符", groups = ValidationGroups.Common.class)
    @TableField("menu_name")
    private String menuName;

    /**
     * 父节点id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空", groups = ValidationGroups.Common.class)
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @Size(max = 200, message = "路由地址不能超过200个字符", groups = ValidationGroups.Common.class)
    @TableField("path")
    private String path;

    /**
     * 组件路径
     */
    @Size(max = 200, message = "组件路径不能超过255个字符", groups = ValidationGroups.Common.class)
    @TableField("component")
    private String component;

    /**
     * 路由参数
     */
    @TableField("query_param")
    private String queryParam;

    /**
     * 是否为外链（0是 1否）
     */
    @TableField("is_frame")
    private Integer isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @TableField("is_cache")
    private Integer isCache;

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    @NotBlank(message = "菜单类型不能为空", groups = ValidationGroups.Common.class)
    @TableField("menu_type")
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    @TableField("visible")
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    @TableField("status")
    private String status;

    /**
     * 权限字符串
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField("perms")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
