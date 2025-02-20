package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.common.core.domain.R;
import com.you.common.log.annotation.OperLog;
import com.you.common.log.enums.OperLogTypEnum;
import com.you.system.domain.model.SysNotice;
import com.you.system.domain.qo.NoticeQo;
import com.you.system.service.SysNoticeService;
import com.you.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @OperLog(title = "通知公告", type = OperLogTypEnum.INSERT)
    @SaCheckPermission("system:notice:add")
    @PostMapping
    public R<Void> add(@Validated(ValidationGroups.Add.class) @RequestBody SysNotice notice) {
        return noticeService.saveNotice(notice) ? R.ok() : R.fail();
    }

    @OperLog(title = "通知公告", type = OperLogTypEnum.DELETE)
    @DeleteMapping("/{noticeIds}")
    @SaCheckPermission("system:notice:remove")
    public R<Void> delete(@PathVariable Long[] noticeIds) {
        return noticeService.deleteNoticeByIds(noticeIds) ? R.ok() : R.fail();
    }

    @GetMapping("/{noticeId}")
    @SaCheckPermission("system:notice:query")
    public R<SysNotice> getInfoById(@PathVariable Long noticeId) {
        return R.ok(noticeService.getById(noticeId));
    }

    @PutMapping
    @SaCheckPermission("system:notice:edit")
    public R<Void> edit(@Validated(ValidationGroups.Update.class) @RequestBody SysNotice notice) {
        return noticeService.updateNotice(notice) ? R.ok() : R.fail();
    }
}
