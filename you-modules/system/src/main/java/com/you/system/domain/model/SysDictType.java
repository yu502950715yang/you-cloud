package com.you.system.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.you.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@TableName("sys_dict_type")
public class SysDictType {

    @TableId(value = "dict_id", type = IdType.AUTO)
    @NotNull(message = "字典主键不能为空", groups = ValidationGroups.Update.class)
    private Long dictId;

    @NotNull(message = "字典名称不能为空", groups = ValidationGroups.Common.class)
    @Size(max = 100, message = "字典名称最多100字符", groups = ValidationGroups.Common.class)
    @TableField("dict_name")
    private String dictName;

    @NotNull(message = "字典类型不能为空", groups = ValidationGroups.Common.class)
    @Size(max = 100, message = "字典类型最多100字符", groups = ValidationGroups.Common.class)
    @TableField("dict_type")
    private String dictType;

    @NotNull(message = "状态不能为空", groups = ValidationGroups.Common.class)
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

    @Size(max = 500, message = "备注最多500字符", groups = ValidationGroups.Common.class)
    @TableField("remark")
    private String remark;
}
