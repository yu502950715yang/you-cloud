package com.you.system.domain.qo;

import com.you.common.core.domain.BaseQo;
import com.you.system.domain.vo.SysUserVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQo extends BaseQo<SysUserVo> {

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态
     */
    private String status;

    private String beginTime;

    private String endTime;

    /**
     * 部门查询in 条件
     */
    private String deptIdsStr;
}
