package com.you.system.qo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    private Page<SysRole> page;
}
