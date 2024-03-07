package com.you.system.service.impl;

import com.you.common.core.constant.UserConstants;
import com.you.system.mapper.AuthMapper;
import com.you.system.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;

    public AuthServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public List<String> getPermissions(Long userId) {
        if (UserConstants.ADMIN_ID.equals(userId)) {
            return authMapper.selectAllPerm();
        }
        return authMapper.selectPermTokenByUserId(userId);
    }

    @Override
    public List<String> getRoles(Long userId) {
        if (UserConstants.ADMIN_ID.equals(userId)) {
            return authMapper.selectAllPerm();
        }
        return authMapper.selectRolesByUserId(userId);
    }
}
