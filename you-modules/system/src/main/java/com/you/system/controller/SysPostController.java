package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.you.common.core.exception.CommonException;
import com.you.common.core.model.R;
import com.you.system.model.SysPost;
import com.you.system.qo.PostQo;
import com.you.system.service.SysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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
        List<SysPost> list = sysPostService.selectList(qo);
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = null;
        try {
            fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcelFactory.write(response.getOutputStream(), SysPost.class).sheet("模板").doWrite(list);
        } catch (IOException e) {
            throw new CommonException(e);
        }

    }
}
