package com.you.system.api.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_oper_log")
public class SysOperLog {

    @TableId(value = "oper_id", type = IdType.AUTO)
    private Long operId;

    @TableField("title")
    private String title;

    @TableField("business_type")
    private String businessType;

    @TableField("method")
    private String method;

    @TableField("request_method")
    private String requestMethod;

    @TableField("operator_type")
    private Integer operatorType;

    @TableField("oper_name")
    private String operName;

    @TableField("oper_url")
    private String operUrl;

    @TableField("oper_ip")
    private String operIp;

    @TableField("oper_param")
    private String operParam;

    @TableField("json_result")
    private String jsonResult;

    @TableField("status")
    private Integer status;

    @TableField("error_msg")
    private String errorMsg;

    @TableField("oper_time")
    private LocalDateTime operTime;

    @TableField("reponse_time")
    private Long reponseTime;
}
