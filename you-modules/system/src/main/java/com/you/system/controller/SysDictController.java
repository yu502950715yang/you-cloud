package com.you.system.controller;

import com.you.common.core.model.R;
import com.you.system.model.SysDictData;
import com.you.system.service.SysDictDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class SysDictController {

    private final SysDictDataService dictDataService;

    public SysDictController(SysDictDataService dictDataService) {
        this.dictDataService = dictDataService;
    }

    @GetMapping("/type/{dictType}")
    public R<List<SysDictData>> getDictDataByType(@PathVariable String dictType) {
        return R.ok(dictDataService.getDictDataByType(dictType));
    }
}
