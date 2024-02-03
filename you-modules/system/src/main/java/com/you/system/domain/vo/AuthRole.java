package com.you.system.domain.vo;

import com.you.common.core.domain.model.SysRole;
import com.you.common.core.domain.model.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class AuthRole {

    private SysUser user;

    private List<SysRole> userRoleList;

    private List<SysRole> roleList;
}
