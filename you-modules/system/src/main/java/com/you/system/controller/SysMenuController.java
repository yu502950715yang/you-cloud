package com.you.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class SysMenuController {

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
//    @GetMapping("/getRouters")
//    public R<List<RouterVo>> getRouters() {
//        Long userId = LoginHelper.getUserId();
//        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
//        return R.ok(menuService.buildMenus(menus));
//    }
}
