package com.you.system.service;

import java.util.List;

public interface AuthService {

    /**
     * 获取用户权限
     *
     * @param userId 用户id
     * @return 权限list
     */
    List<String> getPermissions(Long userId);

    /**
     * 获取用户角色key
     * @param userId 用户id
     * @return 角色key List
     */
    List<String> getRoles(Long userId);
}
