package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.you.common.core.constant.Constants;
import com.you.common.core.model.R;
import com.you.system.model.SysDept;
import com.you.system.qo.DeptQo;
import com.you.system.service.SysDeptService;
import com.you.validation.ValidationGroups;
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
        return R.ok(deptService.listPage(qo));
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
}
