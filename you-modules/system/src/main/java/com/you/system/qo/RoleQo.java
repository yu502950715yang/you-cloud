package com.you.system.qo;

import com.you.common.model.PageModel;
import com.you.system.model.SysRole;
import lombok.Data;

@Data
public class RoleQo {

    private String roleName;

    private String roleKey;

    // 角色状态（0正常 1停用）
    private String status;

    private String beginTime;

    private String endTime;

    private PageModel<SysRole> page;
}