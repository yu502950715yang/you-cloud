package com.you.system.domain.qo;

import com.you.common.core.domain.model.SysUser;
import com.you.common.model.PageModel;
import lombok.Data;

@Data
public class AuthUserQo {

    /**
     * 用户账号
     */
    private String username;

    /**
     * 电话
     */
    private String phone;

    /**
     * 角色id
     */
    private Long roleId;

    private PageModel<SysUser> page;
}
