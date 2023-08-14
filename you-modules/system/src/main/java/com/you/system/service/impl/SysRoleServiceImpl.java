package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.mapper.SysRoleMapper;
import com.you.system.model.SysRole;
import com.you.system.qo.RoleQo;
import com.you.system.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper roleMapper;

    public SysRoleServiceImpl(SysRoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public IPage<SysRole> listPage(RoleQo roleQo) {
        return roleMapper.listPage(roleQo.getPage(), roleQo);
    }
}
