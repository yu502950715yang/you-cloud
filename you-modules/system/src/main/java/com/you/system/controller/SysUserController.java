package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.you.common.core.model.R;
import com.you.system.model.LoginUser;
import com.you.system.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-12 14:20
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("/{username}")
    public R<LoginUser> getUserByUsername(@PathVariable("username") String username) {
        LoginUser user = sysUserService.getLoginUser(username);
        return R.ok(user);
    }

    @SaCheckLogin
    @GetMapping("/test")
    public R<?> getTest() {
        System.out.println(StpUtil.getPermissionList());
        return R.ok(StpUtil.getLoginId());
    }
}
