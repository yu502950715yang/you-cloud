package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.common.core.constant.UserConstants;
import com.you.common.core.exception.CommonException;
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

    @Override
    public void checkRoleAllowed(Long roleId, String roleKey) {
        if (UserConstants.ADMIN_ID.equals(roleId)) {
            throw new CommonException("不允许操作超级管理员角色");
        }
        SysRole role = roleMapper.selectById(roleId);
        // 新增不允许使用 管理员标识符
        if (roleId == null && UserConstants.ADMIN_ROLE_KEY.equals(role.getRoleKey())) {
            throw new CommonException("不允许使用系统内置管理员角色标识符");
        }
        // 修改不允许修改 管理员标识符
        if (role.getRoleId() != null) {
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
}
