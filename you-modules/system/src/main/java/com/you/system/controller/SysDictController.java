package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.constant.Constants;
import com.you.common.core.domain.R;
import com.you.common.log.annotation.OperLog;
import com.you.common.log.enums.OperLogTypenum;
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
@RequestMapping("/dict")
public class SysDictController {

    private final SysDictDataService dictDataService;
    private final SysDictTypeService dictTypeService;

    @GetMapping("/type/{dictType}")
    public R<List<SysDictData>> getDictDataByType(@PathVariable String dictType) {
        return R.ok(dictDataService.getDictDataByType(dictType));
    }

    @OperLog(title = "字典管理", type = OperLogTypenum.QUERY)
    @SaCheckPermission("system:dict:list")
    @PostMapping("/type/list")
    public R<IPage<SysDictType>> getDictTypeList(@RequestBody DictTypeQo qo) {
        return R.ok(dictTypeService.listPage(qo));
    }

    @OperLog(title = "字典管理", type = OperLogTypenum.INSERT)
    @SaCheckPermission("system:post:add")
    @PostMapping("/type/save")
    public R<Void> saveDictType(@Validated(ValidationGroups.Add.class) @RequestBody SysDictType dictType) {
        if (!dictTypeService.checkDictTypeUnique(dictType)) {
            return R.fail("字典类型已存在");
        }
        dictType.setCreateBy(LoginUtils.getLoginUserName());
        dictType.setCreateTime(LocalDateTime.now());
        return dictTypeService.save(dictType) ? R.ok() : R.fail(Constants.REQUEST_FAIL_MSG);
    }
}
