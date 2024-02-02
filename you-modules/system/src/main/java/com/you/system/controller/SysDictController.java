package com.you.system.controller;

import com.you.common.core.domain.R;
import com.you.system.domain.model.SysDictData;
import com.you.system.service.SysDictDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dict")
public class SysDictController {

    private final SysDictDataService dictDataService;

    @GetMapping("/type/{dictType}")
    public R<List<SysDictData>> getDictDataByType(@PathVariable String dictType) {
        return R.ok(dictDataService.getDictDataByType(dictType));
    }
}
