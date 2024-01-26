package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.common.core.constant.UserConstants;
import com.you.common.core.exception.CommonException;
import com.you.system.domain.bo.SysRoleBo;
import com.you.system.domain.qo.RoleQo;
import com.you.system.mapper.SysRoleMapper;
import com.you.system.model.SysRole;
import com.you.system.service.SysRoleMenuService;
import com.you.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper roleMapper;
    private final SysRoleMenuService sysRoleMenuService;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean edit(SysRoleBo role) {
        int result = roleMapper.updateById(role);
        // 删除原有角色菜单关联表
        sysRoleMenuService.deleteByRoleId(role.getRoleId());
        // 新增关联关系
        if (role.getMenuIds() != null && !role.getMenuIds().isEmpty()) {
            sysRoleMenuService.batchSave(role.getRoleId(), role.getMenuIds());
        }
        return result > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            throw new CommonException("请选择要删除的角色");
        }
        List<SysRoleBo> roleList = roleMapper.selectByRoleIds(roleIds);
        roleList.forEach(roleBo -> {
            // 检查所选角色能否删除
            checkRoleAllowed(roleBo.getRoleId(), null);
            // 检查角色是否关联用户
            if (roleBo.getUserCount() > 0) {
                throw new CommonException(String.format("%1$s已分配用户,不能删除", roleBo.getRoleName()));
            }
        });
        // 删除角色关联菜单表
        sysRoleMenuService.deleteByRoleIds(roleIds);
        int result = roleMapper.deleteBatchIds(roleIds);
        if (result != roleIds.size()) {
            throw new CommonException("角色删除失败");
        }
        return true;
    }
}
