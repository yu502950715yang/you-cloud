package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.common.core.domain.R;
import com.you.common.log.annotation.OperLog;
import com.you.common.log.enums.OperLogTypenum;
import com.you.system.api.domain.model.SysDictData;
import com.you.system.domain.model.SysDictType;
import com.you.system.domain.qo.DictTypeQo;
import com.you.system.service.SysDictDataService;
import com.you.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
