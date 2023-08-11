package com.you.gateway.service;

import com.you.gateway.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final SysUserMapper userMapper;

    public UserServiceImpl(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<String> getPermissions(String userId) {
        return userMapper.selectPermTokenByUserId(userId);
    }
}
