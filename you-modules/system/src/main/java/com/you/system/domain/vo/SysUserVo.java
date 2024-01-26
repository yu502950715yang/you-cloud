package com.you.system.domain.vo;

import com.you.system.model.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserVo extends SysUser {

    /**
     * 部门名称
     */
    private String deptName;
}
