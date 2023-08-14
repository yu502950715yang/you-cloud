package com.you.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.you.common.core.model.R;
import com.you.system.service.SysMenuService;
import com.you.system.vo.MenuTree;
import com.you.system.vo.RouterVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class SysMenuController {

    private final SysMenuService menuService;

    public SysMenuController(SysMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<MenuTree> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildRouter(menus));
    }
}
