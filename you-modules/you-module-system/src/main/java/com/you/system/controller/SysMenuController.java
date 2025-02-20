package com.you.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.constant.UserConstants;
import com.you.common.core.domain.R;
import com.you.common.core.utils.StrUtils;
import com.you.common.log.annotation.OperLog;
import com.you.common.log.enums.OperLogTypEnum;
import com.you.system.domain.model.SysMenu;
import com.you.system.domain.qo.MenuQo;
import com.you.system.domain.vo.ElTree;
import com.you.system.domain.vo.MenuTree;
import com.you.system.domain.vo.RouterVo;
import com.you.system.service.SysMenuService;
import com.you.system.service.SysRoleMenuService;
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

    @OperLog(title = "菜单管理", type = OperLogTypEnum.INSERT)
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
        return R.okOrFail(menuService.save(menu));
    }

    /**
     * 删除菜单
     */
    @OperLog(title = "菜单管理", type = OperLogTypEnum.DELETE)
    @SaCheckPermission("system:menu:remove")
    @DeleteMapping("/{menuId}")
    public R<Void> remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.fail("存在子菜单,不允许删除");
        }
        if (roleMenuService.checkMenuInUse(menuId)) {
            return R.fail("菜单已分配,不允许删除");
        }
        return R.okOrFail(menuService.removeById(menuId));
    }

    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public R<SysMenu> getInfo(@PathVariable Long menuId) {
        return R.ok(menuService.getById(menuId));
    }

    @OperLog(title = "菜单管理", type = OperLogTypEnum.UPDATE)
    @SaCheckPermission("system:menu:edit")
    @PutMapping
    public R<Void> edit(@Validated(ValidationGroups.Update.class) @RequestBody SysMenu menu) {
        String errorMsgTitle = "修改菜单";
        if (!menuService.checkMenuNameUnique(menu)) {
            return R.fail(errorMsgTitle + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME == menu.getIsFrame() && !StrUtils.ishttp(menu.getPath())) {
            return R.fail(errorMsgTitle + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return R.fail(errorMsgTitle + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateTime(LocalDateTime.now());
        menu.setUpdateBy(LoginUtils.getLoginUserName());
        return R.okOrFail(menuService.updateById(menu));
    }
}
