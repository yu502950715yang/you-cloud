package com.you.system.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.you.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 通知公告表 sys_notice
 */
@TableName("sys_notice")
@Data
public class SysNotice {

    /**
     * 公告ID
     */
    @TableId(value = "notice_id", type = IdType.AUTO)
    @NotNull(message = "公告Id不能为空", groups = ValidationGroups.Update.class)
    private Long noticeId;

    /**
     * 公告标题
     */
    @TableField("notice_title")
    @NotNull(message = "公告标题不能为空", groups = ValidationGroups.Common.class)
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    @TableField("notice_type")
    @NotNull(message = "公告类型不能为空", groups = ValidationGroups.Common.class)
    private String noticeType;

    /**
     * 公告内容
     */
    @TableField("notice_content")
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    @TableField("status")
    private String status;

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
