package com.you.system.qo;

import com.you.common.model.PageModel;
import com.you.system.model.SysUser;
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
