package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.constant.UserConstants;
import com.you.common.core.model.R;
import com.you.common.core.utils.StrUtils;
import com.you.system.model.SysMenu;
import com.you.system.qo.MenuQo;
import com.you.system.service.SysMenuService;
import com.you.system.service.SysRoleMenuService;
import com.you.system.vo.ElTree;
import com.you.system.vo.MenuTree;
import com.you.system.vo.RouterVo;
import com.you.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    private final SysMenuService menuService;
    private final SysRoleMenuService roleMenuService;

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

    @GetMapping("/treeSelect")
    public R<List<ElTree>> treeSelect() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<ElTree> treeList = menuService.selectMenuList(userId);
        return R.ok(treeList);
    }

    @SaCheckPermission("system:menu:list")
    @GetMapping("/list")
    public R<List<SysMenu>> list(MenuQo qo) {
        return R.ok(menuService.selectMenuList(qo));
    }

    @SaCheckPermission("system:menu:add")
    @PostMapping
    public R<Void> add(@Validated(ValidationGroups.Add.class) @RequestBody SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME == menu.getIsFrame() && !StrUtils.ishttp(menu.getPath())) {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(LoginUtils.getLoginUserName());
        menu.setCreateTime(LocalDateTime.now());
        if (menuService.save(menu)) {
            return R.ok();
        }
        return R.fail("新增菜单失败");
    }

    /**
     * 删除菜单
     */
    @SaCheckPermission("system:menu:remove")
    @DeleteMapping("/{menuId}")
    public R<Void> remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.fail("存在子菜单,不允许删除");
        }
        if (roleMenuService.checkMenuInUse(menuId)) {
            return R.fail("菜单已分配,不允许删除");
        }
        if (menuService.removeById(menuId)) {
            return R.ok();
        }
        return R.fail("删除菜单失败");
    }

    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public R<SysMenu> getInfo(@PathVariable Long menuId) {
        return R.ok(menuService.getById(menuId));
    }
}
