package com.you.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
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
        if (roleId == null || CollectionUtil.isEmpty(userIds)) {
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
        if (roleId == null || CollectionUtil.isEmpty(userIds)) {
            return true;
        }
        return sysUserRoleMapper.insertUserRole(roleId, userIds) == userIds.size();
    }

    @Override
    public boolean saveUserRoles(Long userId, List<Long> roleIds) {
        if (userId == null || CollectionUtil.isEmpty(roleIds)) {
            return false;
        }
        return sysUserRoleMapper.insertUserRoles(userId, roleIds) == roleIds.size();
    }

    @Override
    public void removeByUserIds(List<Long> userIds) {
        if (CollectionUtil.isNotEmpty(userIds)) {
            sysUserRoleMapper.deleteByUserIds(userIds);
        }
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return sysUserRoleMapper.selectRoleIdsByUserId(userId);
    }
}
