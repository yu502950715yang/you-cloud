package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.constant.Constants;
import com.you.common.core.domain.R;
import com.you.common.core.exception.CommonException;
import com.you.system.domain.model.SysPost;
import com.you.system.domain.poi.SysPostExcel;
import com.you.system.domain.qo.PostQo;
import com.you.system.service.SysPostService;
import com.you.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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

    @SaCheckPermission("system:post:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PostQo qo) {
        List<SysPostExcel> list = sysPostService.selectExcelList(qo);
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName;
        try {
            fileName = URLEncoder.encode("岗位", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcelFactory.write(response.getOutputStream(), SysPostExcel.class).sheet("岗位").doWrite(list);
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

    /**
     * 新增岗位
     */
    @SaCheckPermission("system:post:add")
    @PostMapping
    public R<Void> add(@Validated(ValidationGroups.Add.class) @RequestBody SysPost post) {
        if (!sysPostService.checkPostNameUnique(post)) {
            return R.fail("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (!sysPostService.checkPostCodeUnique(post)) {
            return R.fail("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateTime(LocalDateTime.now());
        post.setCreateBy(LoginUtils.getLoginUserName());
        if (sysPostService.save(post)) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @SaCheckPermission("system:post:remove")
    @DeleteMapping("/{postIds}")
    public R<Void> remove(@PathVariable Long[] postIds) {
        if (postIds == null || postIds.length <= 0) {
            return R.fail("ID不能为空");
        }
        List<Long> postIdList = Arrays.asList(postIds);
        if (sysPostService.removeByIds(postIdList)) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @SaCheckPermission("system:post:query")
    @GetMapping(value = "/{postId}")
    public R<SysPost> getInfo(@PathVariable Long postId) {
        return R.ok(sysPostService.getById(postId));
    }

    @SaCheckPermission("system:post:edit")
    @PutMapping
    public R<Void> edit(@Validated(ValidationGroups.Update.class) @RequestBody SysPost post) {
        if (!sysPostService.checkPostNameUnique(post)) {
            return R.fail("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (!sysPostService.checkPostCodeUnique(post)) {
            return R.fail("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateTime(LocalDateTime.now());
        post.setUpdateBy(LoginUtils.getLoginUserName());
        if (sysPostService.updateById(post)) {
            return R.ok();
        }
        return R.fail(Constants.REQUEST_FAIL_MSG);
    }

    @GetMapping("/getAll")
    public R<List<SysPost>> getAll() {
        return R.ok(sysPostService.getAllPost());
    }
}
