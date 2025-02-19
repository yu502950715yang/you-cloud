package com.you.system.domain.qo;

import com.you.common.core.domain.BaseQo;
import com.you.system.api.domain.model.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthUserQo extends BaseQo<SysUser> {

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
}
