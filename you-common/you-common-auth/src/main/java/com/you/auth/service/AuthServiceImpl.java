package com.you.auth.service;

import com.you.auth.mapper.AuthMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;

    public AuthServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public List<String> getPermissions(String userId) {
        return authMapper.selectPermTokenByUserId(userId);
    }

    @Override
    public List<String> getRoles(String userId) {
        return authMapper.selectRolesByUserId(userId);
    }
}
