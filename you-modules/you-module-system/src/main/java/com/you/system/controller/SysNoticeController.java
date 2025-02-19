package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.common.core.domain.R;
import com.you.system.domain.model.SysNotice;
import com.you.system.domain.qo.NoticeQo;
import com.you.system.service.SysNoticeService;
import com.you.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知公告信息
 */
@RequestMapping("/notice")
@RestController
@RequiredArgsConstructor
public class SysNoticeController {

    private final SysNoticeService noticeService;

    @PostMapping("/list")
    @SaCheckPermission("system:notice:list")
    public R<IPage<SysNotice>> listPage(@RequestBody NoticeQo qo) {
        return R.ok(noticeService.listPage(qo));
    }

    @SaCheckPermission("system:notice:add")
    @PostMapping
    public R<Void> add(@Validated(ValidationGroups.Add.class) @RequestBody SysNotice notice) {
        return noticeService.saveNotice(notice) ? R.ok() : R.fail();
    }
}
