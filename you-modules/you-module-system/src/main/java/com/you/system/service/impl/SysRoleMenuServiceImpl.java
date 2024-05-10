package com.you.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.you.system.mapper.SysRoleMenuMapper;
import com.you.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

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

    @Override
    public void deleteByRoleId(Long roleId) {
        sysRoleMenuMapper.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        if (CollectionUtil.isNotEmpty(roleIds)) {
            sysRoleMenuMapper.deleteByRoleIds(roleIds);
        }
    }

    @Override
    public boolean checkMenuInUse(Long menuId) {
        return sysRoleMenuMapper.selectCountByMenuId(menuId) > 0;
    }
}
