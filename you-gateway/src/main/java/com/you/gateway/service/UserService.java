package com.you.gateway.service;

import java.util.List;

public interface UserService {

    /**
     * 获取用户权限
     *
     * @param userId 用户id
     * @return 权限list
     */
    List<String> getPermissions(String userId);
}
