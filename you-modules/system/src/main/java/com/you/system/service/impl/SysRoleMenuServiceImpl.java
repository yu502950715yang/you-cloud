package com.you.system.service.impl;

import com.you.system.mapper.SysRoleMenuMapper;
import com.you.system.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    public SysRoleMenuServiceImpl(SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysRoleMenuMapper = sysRoleMenuMapper;
    }

    @Override
    public void batchSave(long roleId, List<Long> menuIdList) {
        if (menuIdList != null && !menuIdList.isEmpty()) {
            sysRoleMenuMapper.batchInsert(roleId, menuIdList);
        }
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return sysRoleMenuMapper.selectMenuIds(roleId);
    }
}
