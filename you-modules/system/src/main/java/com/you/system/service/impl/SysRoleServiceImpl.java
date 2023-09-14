package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.common.core.constant.UserConstants;
import com.you.common.core.exception.CommonException;
import com.you.system.bo.SysRoleBo;
import com.you.system.mapper.SysRoleMapper;
import com.you.system.model.SysRole;
import com.you.system.qo.RoleQo;
import com.you.system.service.SysRoleMenuService;
import com.you.system.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper roleMapper;
    private final SysRoleMenuService sysRoleMenuService;

    public SysRoleServiceImpl(SysRoleMapper roleMapper, SysRoleMenuService sysRoleMenuService) {
        this.roleMapper = roleMapper;
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Override
    public IPage<SysRole> listPage(RoleQo roleQo) {
        return roleMapper.listPage(roleQo.getPage(), roleQo);
    }

    @Override
    public void checkRoleAllowed(Long roleId, String roleKey) {
        if (UserConstants.ADMIN_ID.equals(roleId)) {
            throw new CommonException("不允许操作超级管理员角色");
        }
        // 新增不允许使用 管理员标识符
        if (roleId == null && UserConstants.ADMIN_ROLE_KEY.equals(roleKey)) {
            throw new CommonException("不允许使用系统内置管理员角色标识符");
        }
        // 修改不允许修改 管理员标识符
        if (roleId != null) {
            SysRole role = roleMapper.selectById(roleId);
            // 如果标识符不相等 判断为修改了管理员标识符
            if (!role.getRoleKey().equals(roleKey) && UserConstants.ADMIN_ROLE_KEY.equals(role.getRoleKey())) {
                throw new CommonException("不允许修改系统内置管理员角色标识符!");
            }
        }
    }

    @Override
    public boolean updateRoleStatus(Long roleId, String status) {
        return roleMapper.updateState(roleId, status) > 0;
    }

    @Override
    public void checkRoleNameUnique(Long roleId, String roleName) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRole::getRoleName, roleName);
        SysRole role = roleMapper.selectOne(queryWrapper);
        if (role != null && !role.getRoleId().equals(roleId)) {
            throw new CommonException("角色名已存在");
        }
    }

    @Override
    public void checkRoleKeyUnique(Long roleId, String roleKey) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRole::getRoleKey, roleKey);
        SysRole role = roleMapper.selectOne(queryWrapper);
        if (role != null && !role.getRoleId().equals(roleId)) {
            throw new CommonException("权限字符串已存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(SysRoleBo role) {
        roleMapper.insert(role);
        if (role.getMenuIds() != null && !role.getMenuIds().isEmpty()) {
            sysRoleMenuService.batchSave(role.getRoleId(), role.getMenuIds());
        }
        return role.getRoleId() != null;
    }

    @Override
    public SysRoleBo getRoleById(Long roleId) {
        SysRole role = roleMapper.selectById(roleId);
        SysRoleBo bo = new SysRoleBo();
        if (role != null) {
            BeanUtils.copyProperties(role, bo);
            bo.setMenuIds(sysRoleMenuService.getMenuIdsByRoleId(roleId));
        }
        return bo;
    }
}
