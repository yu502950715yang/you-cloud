package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.you.common.core.model.R;
import com.you.system.model.SysDept;
import com.you.system.qo.DeptQo;
import com.you.system.service.SysDeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
