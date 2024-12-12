package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.common.core.domain.R;
import com.you.system.api.domain.model.SysDictData;
import com.you.system.domain.qo.DictDataQo;
import com.you.system.service.SysDictDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/dict/data")
public class SysDictDataController {

    private final SysDictDataService dictDataService;

    @SaCheckPermission("system:dict:list")
    @PostMapping("/list")
    public R<IPage<SysDictData>> list(@RequestBody DictDataQo qo) {
        return R.ok(dictDataService.listPage(qo));
    }
}
