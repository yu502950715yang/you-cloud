package com.you.auth.service;

import java.util.List;

public interface AuthService {

    /**
     * 获取用户权限
     *
     * @param userId 用户id
     * @return 权限list
     */
    List<String> getPermissions(String userId);
}
