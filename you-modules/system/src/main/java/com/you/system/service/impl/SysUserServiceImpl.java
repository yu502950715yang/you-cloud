package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.auth.service.AuthService;
import com.you.system.domain.model.SysDept;
import com.you.system.domain.qo.AuthUserQo;
import com.you.system.domain.qo.UserQo;
import com.you.system.domain.vo.SysUserVo;
import com.you.system.mapper.SysUserMapper;
import com.you.system.model.LoginUser;
import com.you.system.model.SysUser;
import com.you.system.service.SysDeptService;
import com.you.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;
    private final AuthService authService;
    private final SysDeptService deptService;

    @Override
    public SysUser getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public LoginUser getLoginUserByUserId(Long userId) {
        LoginUser loginUser = new LoginUser(userMapper.selectLoginUserByUserId(userId));
        loginUser.setPermissions(authService.getPermissions(userId));
        return loginUser;
    }

    @Override
    public IPage<SysUser> ruleAllocatedListPage(AuthUserQo qo) {
        return userMapper.ruleAllocatedListPage(qo.getPage(), qo);
    }

    @Override
    public IPage<SysUser> ruleUnallocatedListPage(AuthUserQo qo) {
        return userMapper.ruleUnallocatedListPage(qo.getPage(), qo);
    }

    @Override
    public IPage<SysUserVo> listPage(UserQo qo) {
        if (qo.getDeptId() != null) {
            List<SysDept> children = deptService.getAllChildByDeptId(qo.getDeptId());
            String childrenIds = children.stream()
                    .map(d -> String.valueOf(d.getDeptId()))
                    .collect(Collectors.joining(","));
            if (!children.isEmpty()) {
                qo.setDeptIdsStr(childrenIds + "," + qo.getDeptId());
            } else {
                qo.setDeptIdsStr(String.valueOf(qo.getDeptId()));
            }
        }
        return userMapper.listPage(qo.getPage(), qo);
    }
}
