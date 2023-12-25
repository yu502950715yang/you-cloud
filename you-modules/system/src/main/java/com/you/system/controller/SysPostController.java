package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.common.core.model.R;
import com.you.system.model.SysPost;
import com.you.system.qo.PostQo;
import com.you.system.service.SysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 岗位信息
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class SysPostController {

    private final SysPostService sysPostService;

    @SaCheckPermission("system:post:list")
    @PostMapping("/list")
    public R<IPage<SysPost>> list(@RequestBody PostQo qo) {
        return R.ok(sysPostService.listPage(qo));
    }
}
