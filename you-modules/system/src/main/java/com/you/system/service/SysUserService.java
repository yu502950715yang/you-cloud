package com.you.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.model.LoginUser;
import com.you.system.model.SysUser;

public interface SysUserService extends IService<SysUser> {

    /**
     * 根据username获取用户信息
     *
     * @param username 登录名
     * @return 用户
     */
    SysUser getUserByUsername(String username);

    /**
     * 根据userId获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    LoginUser getLoginUserByUserId(Long userId);
}
