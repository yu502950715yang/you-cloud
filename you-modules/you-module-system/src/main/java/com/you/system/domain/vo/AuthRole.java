package com.you.system.domain.vo;

import com.you.system.api.domain.model.SysRole;
import com.you.system.api.domain.model.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class AuthRole {

    private SysUser user;

    private List<SysRole> userRoleList;

    private List<SysRole> roleList;
}
