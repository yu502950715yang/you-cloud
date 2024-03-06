package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.constant.Constants;
import com.you.common.core.domain.R;
import com.you.common.log.annotation.OperLog;
import com.you.common.log.enums.OperLogTypEnum;
import com.you.system.api.domain.model.SysDictData;
import com.you.system.domain.model.SysDictType;
import com.you.system.domain.qo.DictTypeQo;
import com.you.system.service.SysDictDataService;
import com.you.system.service.SysDictTypeService;
import com.you.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dict/type")
public class SysDictTypeController {

    private final SysDictDataService dictDataService;
    private final SysDictTypeService dictTypeService;

    @GetMapping("/{dictType}")
    public R<List<SysDictData>> getDictDataByType(@PathVariable String dictType) {
        return R.ok(dictDataService.getDictDataByType(dictType));
    }

    @OperLog(title = "字典管理", type = OperLogTypEnum.QUERY)
    @SaCheckPermission("system:dict:list")
    @PostMapping("/list")
    public R<IPage<SysDictType>> getDictTypeList(@RequestBody DictTypeQo qo) {
        return R.ok(dictTypeService.listPage(qo));
    }

    @OperLog(title = "字典管理", type = OperLogTypEnum.INSERT)
    @SaCheckPermission("system:post:add")
    @PostMapping("/save")
    public R<Void> saveDictType(@Validated(ValidationGroups.Add.class) @RequestBody SysDictType dictType) {
        if (!dictTypeService.checkDictTypeUnique(dictType)) {
            return R.fail("字典类型已存在");
        }
        dictType.setCreateBy(LoginUtils.getLoginUserName());
        dictType.setCreateTime(LocalDateTime.now());
        return dictTypeService.save(dictType) ? R.ok() : R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @SaCheckPermission("system:dict:edit")
    @GetMapping("/info/{dictId}")
    public R<SysDictType> getDictTypeInfo(@PathVariable Long dictId) {
        return R.ok(dictTypeService.getById(dictId));
    }

    @OperLog(title = "字典管理", type = OperLogTypEnum.UPDATE)
    @SaCheckPermission("system:dict:edit")
    @PostMapping("/edit")
    public R<Void> editDictType(@Validated(ValidationGroups.Update.class) @RequestBody SysDictType dictType) {
        if (!dictTypeService.checkDictTypeUnique(dictType)) {
            return R.fail("字典类型已存在");
        }
        dictType.setUpdateBy(LoginUtils.getLoginUserName());
        dictType.setUpdateTime(LocalDateTime.now());
        return dictTypeService.editDictType(dictType) ? R.ok() : R.fail(Constants.REQUEST_FAIL_MSG);
    }
}
