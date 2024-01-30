package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.auth.service.AuthService;
import com.you.common.core.constant.UserConstants;
import com.you.common.core.enums.DelFlagEnum;
import com.you.common.core.exception.CommonException;
import com.you.common.core.utils.sm3.SM3Util;
import com.you.system.domain.bo.SysUserBo;
import com.you.system.domain.model.SysDept;
import com.you.system.domain.qo.AuthUserQo;
import com.you.system.domain.qo.UserQo;
import com.you.system.domain.vo.SysUserVo;
import com.you.system.mapper.SysUserMapper;
import com.you.system.model.LoginUser;
import com.you.system.model.SysUser;
import com.you.system.service.SysDeptService;
import com.you.system.service.SysUserPostService;
import com.you.system.service.SysUserRoleService;
import com.you.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;
    private final AuthService authService;
    private final SysDeptService deptService;
    private final SysUserRoleService userRoleService;
    private final SysUserPostService userPostService;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(SysUserBo user) {
        // 新增用户
        user.setPassword(SM3Util.encrypt(user.getPassword()));
        userMapper.insert(user);
        if (user.getUserId() != null) {
            // 新增用户角色关联
            userRoleService.saveUserRoles(user.getUserId(), user.getRoleIds());
            // 新增用户岗位关联
            userPostService.saveUserPosts(user.getUserId(), user.getPostIds());
        }
        return user.getUserId() != null;
    }

    /**
     * 校验用户名是否已存在
     *
     * @param userId   用户id
     * @param username 用户名
     */
    @Override
    public void checkUsernameUnique(Long userId, String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUsername, username)
                .eq(SysUser::getDelFlag, DelFlagEnum.NORMAL.getCode());
        SysUser user = userMapper.selectOne(queryWrapper);
        if (user != null && !user.getUserId().equals(userId)) {
            throw new CommonException("用户名称已存在");
        }
    }

    @Override
    public boolean removeByIds(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return true;
        }
        if (userIds.contains(UserConstants.ADMIN_ID)) {
            throw new CommonException("不允许删除超级管理员用户");
        }
        // 删除用户
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysUser::getDelFlag, DelFlagEnum.DELETE.getCode())
                .in(SysUser::getUserId, userIds);
        return userMapper.update(null, updateWrapper) > 0;
    }
}
