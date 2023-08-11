package com.you.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.mapper.SysUserMapper;
import com.you.system.model.LoginUser;
import com.you.system.model.SysUser;
import com.you.system.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;

    public SysUserServiceImpl(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public SysUser getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public LoginUser getLoginUser(String username) {
        return userMapper.selectLoginUserByUsername(username);
    }

    @Override
    public LoginUser getLoginUserByUserId(String userId) {
        return userMapper.selectLoginUserByUserId(userId);
    }
}
