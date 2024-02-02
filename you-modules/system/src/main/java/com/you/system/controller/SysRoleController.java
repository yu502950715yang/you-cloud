package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.constant.Constants;
import com.you.common.core.domain.R;
import com.you.common.core.domain.model.SysRole;
import com.you.common.core.domain.model.SysUser;
import com.you.system.domain.bo.AuthUserBo;
import com.you.system.domain.bo.SysRoleBo;
import com.you.system.domain.qo.AuthUserQo;
import com.you.system.domain.qo.RoleQo;
import com.you.system.service.SysRoleService;
import com.you.system.service.SysUserRoleService;
import com.you.system.service.SysUserService;
import com.you.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/role")
public class SysRoleController {

    private final SysRoleService roleService;
    private final SysUserService userService;
    private final SysUserRoleService userRoleService;

    @PostMapping("/list")
    @SaCheckPermission("system:role:list")
    public R<IPage<SysRole>> listPage(@RequestBody RoleQo qo) {
        return R.ok(roleService.listPage(qo));
    }

    @PostMapping("/changeStatus")
    @SaCheckPermission("system:role:edit")
    public R<Void> changeStatus(@Validated(ValidationGroups.Other.class) @RequestBody SysRole role) {
        if (roleService.updateRoleStatus(role.getRoleId(), role.getStatus())) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @PostMapping("/save")
    @SaCheckPermission("system:role:add")
    public R<Void> save(@Validated(ValidationGroups.Add.class) @RequestBody SysRoleBo role) {
        // 校验角色名称唯一
        roleService.checkRoleNameUnique(role.getRoleId(), role.getRoleName());
        // 校验权限字符串唯一
        roleService.checkRoleKeyUnique(role.getRoleId(), role.getRoleKey());
        // 构造新增数据
        role.setCreateTime(LocalDateTime.now());
        role.setCreateBy(LoginUtils.getLoginUserName());
        if (roleService.save(role)) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @GetMapping("/{roleId}")
    @SaCheckPermission("system:role:query")
    public R<SysRoleBo> getInfoById(@PathVariable Long roleId) {
        return R.ok(roleService.getRoleById(roleId));
    }

    @PostMapping("/edit")
    @SaCheckPermission("system:role:edit")
    public R<Void> edit(@Validated(ValidationGroups.Update.class) @RequestBody SysRoleBo role) {
        // 校验角色名称唯一
        roleService.checkRoleNameUnique(role.getRoleId(), role.getRoleName());
        // 校验权限字符串唯一
        roleService.checkRoleKeyUnique(role.getRoleId(), role.getRoleKey());
        role.setUpdateTime(LocalDateTime.now());
        role.setUpdateBy(LoginUtils.getLoginUserName());
        if (roleService.edit(role)) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @PostMapping("/remove")
    @SaCheckPermission("system:role:remove")
    public R<Void> remove(@RequestBody List<Long> roleIds) {
        if (roleService.removeByIds(roleIds)) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }


    @PostMapping("/authUser/allocatedList")
    @SaCheckPermission("system:role:edit")
    public R<IPage<SysUser>> allocatedListPage(@RequestBody AuthUserQo qo) {
        return R.ok(userService.ruleAllocatedListPage(qo));
    }

    @PostMapping("/authUser/unallocatedList")
    @SaCheckPermission("system:role:edit")
    public R<IPage<SysUser>> unallocatedListPage(@RequestBody AuthUserQo qo) {
        return R.ok(userService.ruleUnallocatedListPage(qo));
    }

    @PostMapping("/authUser/cancel")
    @SaCheckPermission("system:role:edit")
    public R<Void> authUserCancel(@RequestBody AuthUserBo bo) {
        if (userRoleService.removeRoleByUserIds(bo.getRoleId(), bo.getUserIds())) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @PostMapping("/authUser/select")
    @SaCheckPermission("system:role:edit")
    public R<Void> authUserSelect(@RequestBody AuthUserBo bo) {
        if (userRoleService.saveUserRole(bo.getRoleId(), bo.getUserIds())) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @GetMapping("/getAll")
    public R<List<SysRole>> getAll() {
        return R.ok(roleService.getAllRole());
    }
}
