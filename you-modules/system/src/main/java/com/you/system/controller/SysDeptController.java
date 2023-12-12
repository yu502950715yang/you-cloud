package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.you.common.core.constant.Constants;
import com.you.common.core.model.R;
import com.you.system.model.SysDept;
import com.you.system.qo.DeptQo;
import com.you.system.service.SysDeptService;
import com.you.validation.ValidationGroups;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class SysDeptController {

    private final SysDeptService deptService;

    public SysDeptController(SysDeptService deptService) {
        this.deptService = deptService;
    }

    @SaCheckPermission("system:dept:list")
    @GetMapping("/list")
    public R<List<SysDept>> list(DeptQo qo) {
        return R.ok(deptService.list(qo));
    }

    @SaCheckPermission("system:dept:add")
    @PostMapping
    public R<Void> add(@Validated(ValidationGroups.Add.class) @RequestBody SysDept dept) {
        deptService.checkDeptNameUnique(dept);
        if (deptService.addDept(dept)) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @GetMapping("/list/exclude/{deptId}")
    public R<List<SysDept>> excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> deptList = deptService.list(new DeptQo());
        deptList.removeIf(d -> d.getDeptId().equals(deptId) || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return R.ok(deptList);
    }

    @SaCheckPermission("system:dept:edit")
    @GetMapping(value = "/{deptId}")
    public R<SysDept> getInfo(@PathVariable Long deptId) {
        return R.ok(deptService.getById(deptId));
    }

    /**
     * 修改部门
     */
    @SaCheckPermission("system:dept:edit")
    @PutMapping
    public R<Void> edit(@Validated(ValidationGroups.Update.class) @RequestBody SysDept dept) {
        deptService.checkDeptNameUnique(dept);
        Long deptId = dept.getDeptId();
        if (dept.getParentId().equals(deptId)) {
            return R.fail("上级部门不能是自己");
        }
        if (deptService.editDept(dept)) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @SaCheckPermission("system:dept:remove")
    @DeleteMapping("/{deptId}")
    public R<Void> remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return R.fail("存在下级部门,不允许删除");
        }
        if (deptService.hasUserByDeptId(deptId)) {
            return R.fail("部门存在用户,不允许删除");
        }
        if (deptService.delById(deptId)) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }
}
