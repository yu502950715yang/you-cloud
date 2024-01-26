package com.you.system.domain.qo;

import com.you.common.model.PageModel;
import com.you.system.domain.vo.SysUserVo;
import lombok.Data;

/**
 * 用户查询对象
 */
@Data
public class UserQo {

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

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 部门查询in 条件
     */
    private String deptIdsStr;

    /**
     * 分页
     */
    private PageModel<SysUserVo> page;
}
