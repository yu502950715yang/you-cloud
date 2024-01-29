package com.you.system.service.impl;

import com.you.system.mapper.SysUserRoleMapper;
import com.you.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public boolean removeRoleByUserIds(Long roleId, List<Long> userIds) {
        if (roleId == null || userIds == null || userIds.isEmpty()) {
            return true;
        }
        return sysUserRoleMapper.deleteRoleByUserIds(roleId, userIds) > 0;
    }

    @Override
    public List<Long> getUserIdsByRoleId(Long roleId) {
        if (roleId == null) {
            return new ArrayList<>();
        }
        return sysUserRoleMapper.selectUserIdsByRoleId(roleId);
    }

    @Override
    public boolean saveUserRole(Long roleId, List<Long> userIds) {
        if (roleId == null || userIds == null || userIds.isEmpty()) {
            return true;
        }
        return sysUserRoleMapper.insertUserRole(roleId, userIds) == userIds.size();
    }

    @Override
    public boolean saveUserRoles(Long userId, List<Long> roleIds) {
        if (userId == null || roleIds == null || roleIds.isEmpty()) {
            return false;
        }
        return sysUserRoleMapper.insertUserRoles(userId, roleIds) == roleIds.size();
    }
}
