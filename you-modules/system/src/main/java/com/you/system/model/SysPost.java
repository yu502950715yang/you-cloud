package com.you.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_post")
public class SysPost {

    @TableId(value = "post_id", type = IdType.AUTO)
    private Long postId;

    /**
     * 岗位编码
     */
    @TableField("post_code")
    private String postCode;

    /**
     * 岗位名称
     */
    @TableField("post_name")
    private String postName;

    /**
     * 显示顺序
     */
    @TableField("post_sort")
    private Integer postSort;

    /**
     * 状态（0正常 1停用）
     */
    @TableField("status")
    private String status;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
