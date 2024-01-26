package com.you.auth.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.you.common.core.constant.LoginConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 人员登录工具类
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUtils {

    /**
     * 获取当前登录人的登录账号
     */
    public static String getLoginUserName() {
        Object username = StpUtil.getExtra(LoginConstants.EXTRA_USERNAME);
        if (username != null) {
            return String.valueOf(username);
        }
        return null;
    }
}
