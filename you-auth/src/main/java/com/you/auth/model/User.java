package com.you.auth.model;

import cn.dev33.satoken.stp.SaTokenInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * Description on this file, you will change here.
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-21 09:43
 */
@Getter
@Setter
public class User {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * token信息
     */
    private SaTokenInfo tokenInfo;

    public User(String userId, String username, SaTokenInfo tokenInfo) {
        this.userId = userId;
        this.username = username;
        this.tokenInfo = tokenInfo;
    }
}
