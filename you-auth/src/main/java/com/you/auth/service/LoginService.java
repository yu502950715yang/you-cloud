package com.you.auth.service;

import com.you.system.model.LoginUser;

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
     * @param username
     * @param password
     * @return
     */
    LoginUser login(String username, String password);
}
