package com.you.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.model.SysRole;
import com.you.system.qo.RoleQo;

public interface SysRoleService extends IService<SysRole> {

    IPage<SysRole> listPage(RoleQo roleQo);
}
