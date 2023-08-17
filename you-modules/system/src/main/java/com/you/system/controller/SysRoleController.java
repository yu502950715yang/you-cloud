package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.common.core.constant.Constants;
import com.you.common.core.model.R;
import com.you.system.model.SysRole;
import com.you.system.qo.RoleQo;
import com.you.system.service.SysRoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class SysRoleController {

    private final SysRoleService roleService;

    public SysRoleController(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/list")
    @SaCheckPermission("system:role:list")
    public R<IPage<SysRole>> listPage(@RequestBody RoleQo qo) {
        return R.ok(roleService.listPage(qo));
    }

    @PostMapping("/changeStatus")
    @SaCheckPermission("system:role:edit")
    public R<Void> changeStatus(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role.getRoleId(), role.getRoleKey());
        if (roleService.updateRoleStatus(role.getRoleId(), role.getStatus())) {
            return R.ok();
        } else {
            return R.fail(Constants.REQUEST_FAIL_MSG);
        }
    }
}
