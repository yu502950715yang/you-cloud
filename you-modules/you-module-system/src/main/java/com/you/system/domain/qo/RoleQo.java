package com.you.system.domain.qo;

import com.you.common.core.domain.BaseQo;
import com.you.system.api.domain.model.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQo extends BaseQo<SysRole> {

    private String roleName;

    private String roleKey;

    // 角色状态（0正常 1停用）
    private String status;

    private String beginTime;

    private String endTime;
}
