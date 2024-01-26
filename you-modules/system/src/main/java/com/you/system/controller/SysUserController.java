package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.common.core.model.R;
import com.you.system.model.LoginUser;
import com.you.system.model.SysUser;
import com.you.system.qo.UserQo;
import com.you.system.service.SysUserService;
import com.you.system.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户controller
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-12 14:20
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping("/{username}")
    public R<SysUser> getUserByUsername(@PathVariable("username") String username) {
        SysUser user = sysUserService.getUserByUsername(username);
        return R.ok(user);
    }

    @GetMapping("/getInfo")
    public R<LoginUser> getInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        LoginUser user = sysUserService.getLoginUserByUserId(userId);
        return R.ok(user);
    }

    @SaCheckPermission("system:user:list")
    @PostMapping("/list")
    public R<IPage<SysUserVo>> list(@RequestBody UserQo qo) {
       return R.ok(sysUserService.listPage(qo));
    }
}
