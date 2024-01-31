package com.you.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.auth.service.AuthService;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.constant.UserConstants;
import com.you.common.core.enums.DelFlagEnum;
import com.you.common.core.enums.StatusEnum;
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

import java.time.LocalDateTime;
import java.util.Collections;
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

    @Override
    public boolean changeStatus(SysUser sysUser) {
        if (sysUser.getUserId().equals(UserConstants.ADMIN_ID)) {
            throw new CommonException("不允许修改超级管理员用户");
        }
        sysUser.setUpdateBy(LoginUtils.getLoginUserName());
        sysUser.setUpdateTime(LocalDateTime.now());
        boolean result = userMapper.updateById(sysUser) > 0;
        if (result && sysUser.getStatus().equals(StatusEnum.DISABLE.getCode())) {
            // 如果是禁用踢出指定用户
            StpUtil.logout(sysUser.getUserId());
        }
        return result;
    }

    @Override
    public boolean resetPwd(SysUser sysUser) {
        if (sysUser.getUserId().equals(UserConstants.ADMIN_ID)) {
            throw new CommonException("不允许修改超级管理员用户");
        }
        // 当前登录人id
        Long updateUserId = StpUtil.getLoginIdAsLong();
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysUser::getPassword, SM3Util.encrypt(sysUser.getPassword()))
                .set(SysUser::getUpdateBy, LoginUtils.getLoginUserName())
                .set(SysUser::getUpdateTime, LocalDateTime.now())
                .eq(SysUser::getUserId, sysUser.getUserId());
        boolean result = userMapper.update(null, updateWrapper) > 0;
        if (result && !updateUserId.equals(sysUser.getUserId())) {
            // 重置密码后踢人下线
            StpUtil.logout(sysUser.getUserId());
        }
        return result;
    }

    @Override
    public SysUserBo getUserInfo(Long userId) {
        SysUser sysUser = userMapper.selectById(userId);
        if (sysUser == null) {
            throw new CommonException("用户不存在");
        }
        sysUser.setPassword(null);
        SysUserBo sysUserBo = BeanUtil.copyProperties(sysUser, SysUserBo.class);
        sysUserBo.setRoleIds(userRoleService.getRoleIdsByUserId(sysUser.getUserId()));
        sysUserBo.setPostIds(userPostService.getPostIdsByUserId(sysUser.getUserId()));
        return sysUserBo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean edit(SysUserBo user) {
        if (user.getUserId().equals(UserConstants.ADMIN_ID)) {
            throw new CommonException("不允许修改超级管理员用户");
        }
        // 删除关联表信息
        userRoleService.removeByUserIds(Collections.singletonList(user.getUserId()));
        userPostService.removeByUserIds(Collections.singletonList(user.getUserId()));
        // 修改用户信息
        user.setUsername(null);
        user.setPassword(null);
        boolean result = userMapper.updateById(user) > 0;
        if (result) {
            // 新增用户角色关联
            userRoleService.saveUserRoles(user.getUserId(), user.getRoleIds());
            // 新增用户岗位关联
            userPostService.saveUserPosts(user.getUserId(), user.getPostIds());
        }
        return result;
    }
}
