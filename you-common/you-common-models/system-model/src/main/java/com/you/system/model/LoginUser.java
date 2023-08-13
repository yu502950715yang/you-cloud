package com.you.system.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录用户实体
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 13:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class LoginUser extends SysUser {

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 权限list
     */
    private List<String> permissions;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    public LoginUser(SysUser sysUser) {
        this.setUserId(sysUser.getUserId());
        this.setDeptId(sysUser.getDeptId());
        this.setUsername(sysUser.getUsername());
        this.setNickName(sysUser.getNickName());
        this.setUserType(sysUser.getUserType());
        this.setEmail(sysUser.getEmail());
        this.setPhone(sysUser.getPhone());
        this.setPassword(sysUser.getPassword());
        this.setStatus(sysUser.getStatus());
        this.setDelFlag(sysUser.getDelFlag());
        this.setCreateBy(sysUser.getCreateBy());
        this.setCreateTime(sysUser.getCreateTime());
        this.setUpdateBy(sysUser.getUpdateBy());
        this.setUpdateTime(sysUser.getUpdateTime());
        this.setRemark(sysUser.getRemark());
    }
}
