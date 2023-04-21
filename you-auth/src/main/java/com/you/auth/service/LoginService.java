package com.you.auth.service;

import com.you.auth.model.User;

/**
 * 登录service
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-20 15:25
 */
public interface LoginService {

    /**
     * 根据用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    User login(String username, String password);
}
