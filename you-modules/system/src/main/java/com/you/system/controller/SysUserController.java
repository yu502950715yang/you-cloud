package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.constant.Constants;
import com.you.common.core.domain.R;
import com.you.common.core.domain.model.LoginUser;
import com.you.common.core.domain.model.SysRole;
import com.you.common.core.domain.model.SysUser;
import com.you.common.core.exception.CommonException;
import com.you.system.domain.bo.SysUserBo;
import com.you.system.domain.model.SysDept;
import com.you.system.domain.poi.SysUserExcel;
import com.you.system.domain.qo.UserQo;
import com.you.system.domain.vo.AuthRole;
import com.you.system.domain.vo.SysUserVo;
import com.you.system.service.SysDeptService;
import com.you.system.service.SysRoleService;
import com.you.system.service.SysUserService;
import com.you.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private final SysDeptService deptService;
    private final SysRoleService roleService;

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
        createUserQo(qo);
        return R.ok(sysUserService.listPage(qo));
    }

    private void createUserQo(UserQo qo) {
        if (qo.getDeptId() != null) {
            List<SysDept> children = deptService.getAllChildByDeptId(qo.getDeptId());
            String childrenIds = children.stream()
                    .map(d -> String.valueOf(d.getDeptId()))
                    .collect(Collectors.joining(","));
            if (!children.isEmpty()) {
                qo.setDeptIdsStr(childrenIds + "," + qo.getDeptId());
            } else {
                qo.setDeptIdsStr(String.valueOf(qo.getDeptId()));
            }
        }
    }

    @SaCheckPermission("system:user:add")
    @PostMapping
    public R<Void> addUser(@Validated(ValidationGroups.Add.class) @RequestBody SysUserBo user) {
        // 检查用户名是否唯一
        sysUserService.checkUsernameUnique(user.getUserId(), user.getUsername());
        user.setCreateBy(LoginUtils.getLoginUserName());
        user.setCreateTime(LocalDateTime.now());
        return sysUserService.save(user) ? R.ok() : R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @SaCheckPermission("system:user:remove")
    @PostMapping("/remove")
    public R<Void> remove(@RequestBody List<Long> userIds) {
        return sysUserService.removeByIds(userIds) ? R.ok() : R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @SaCheckPermission("system:user:edit")
    @PostMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody SysUser sysUser) {
        return sysUserService.changeStatus(sysUser) ? R.ok() : R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @SaCheckPermission("system:user:resetPwd")
    @PostMapping("/resetPwd")
    public R<Void> resetPwd(@RequestBody SysUser sysUser) {
        return sysUserService.resetPwd(sysUser) ? R.ok() : R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @SaCheckPermission("system:user:edit")
    @GetMapping("/getUserInfo/{userId}")
    public R<SysUserBo> getUserInfo(@PathVariable("userId") Long userId) {
        return R.ok(sysUserService.getUserInfo(userId));
    }

    @SaCheckPermission("system:user:edit")
    @PostMapping("/edit")
    public R<Void> edit(@Validated(ValidationGroups.Update.class) @RequestBody SysUserBo user) {
        user.setUpdateBy(LoginUtils.getLoginUserName());
        user.setUpdateTime(LocalDateTime.now());
        return sysUserService.edit(user) ? R.ok() : R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @SaCheckPermission("system:user:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody UserQo qo) {
        createUserQo(qo);
        List<SysUserExcel> list = sysUserService.selectExcelList(qo);
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName;
        try {
            fileName = URLEncoder.encode("用户", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcelFactory.write(response.getOutputStream(), SysUserExcel.class).sheet("用户").doWrite(list);
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

    @SaCheckPermission("system:user:edit")
    @GetMapping("/authRole/{userId}")
    public R<AuthRole> authRole(@PathVariable("userId") Long userId) {
        AuthRole authRole = new AuthRole();
        SysUser user = sysUserService.getUserBaseInfo(userId);
        authRole.setUser(user);
        List<SysRole> roleList = roleService.getRoleListByUserId(userId);
        authRole.setUserRoleList(roleList);
        authRole.setRoleList(roleService.getAllRole());
        return R.ok(authRole);
    }

    @SaCheckPermission("system:user:edit")
    @PostMapping("/authRole")
    public R<Void> authRole(@RequestBody SysUserBo user) {
        return sysUserService.updateUserRole(user) ? R.ok() : R.fail(Constants.REQUEST_FAIL_MSG);
    }
}
