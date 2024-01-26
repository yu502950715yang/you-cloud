package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.common.core.constant.UserConstants;
import com.you.system.domain.model.SysMenu;
import com.you.system.domain.qo.MenuQo;
import com.you.system.domain.vo.ElTree;
import com.you.system.domain.vo.MenuTree;
import com.you.system.domain.vo.MetaVo;
import com.you.system.domain.vo.RouterVo;
import com.you.system.mapper.SysMenuMapper;
import com.you.system.service.SysMenuService;
import com.you.system.utils.RouterUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper menuMapper;

    @Override
    public List<MenuTree> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> sysMenuList;
        if (UserConstants.ADMIN_ID.equals(userId)) {
            sysMenuList = menuMapper.selectMenuTypeMCByUserId(null);
        } else {
            sysMenuList = menuMapper.selectMenuTypeMCByUserId(userId);
        }
        return createMenuTree(sysMenuList, 0);
    }

    @Override
    public List<RouterVo> buildRouter(List<MenuTree> menuTrees) {
        List<RouterVo> routerVoList = new ArrayList<>();
        menuTrees.forEach(menuTree -> {
            SysMenu menu = menuTree.getMenu();
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(RouterUtil.getRouteName(menu));
            router.setPath(RouterUtil.getRouterPath(menu));
            router.setComponent(RouterUtil.getComponent(menu));
            router.setQuery(menu.getQueryParam());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), 1 == menu.getIsCache(), menu.getPath()));
            List<MenuTree> cMenus = menuTree.getChildren();
            if (!cMenus.isEmpty() && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildRouter(cMenus));
            } else if (RouterUtil.isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), 1 == menu.getIsCache(), menu.getPath()));
                children.setQuery(menu.getQueryParam());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && RouterUtil.isInnerLink(menu)) {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                String routerPath = RouterUtil.innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routerVoList.add(router);
        });
        return routerVoList;
    }

    @Override
    public List<ElTree> selectMenuList(Long userId) {
        List<SysMenu> sysMenuList;
        if (UserConstants.ADMIN_ID.equals(userId)) {
            sysMenuList = menuMapper.selectMenuByUserId(null);
        } else {
            sysMenuList = menuMapper.selectMenuByUserId(userId);
        }
        return createElTree(sysMenuList, 0);
    }

    @Override
    public List<SysMenu> selectMenuList(MenuQo qo) {
        return menuMapper.selectMenuList(qo);
    }

    @Override
    public boolean checkMenuNameUnique(SysMenu sysMenu) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuName, sysMenu.getMenuName())
                .eq(SysMenu::getParentId, sysMenu.getParentId());
        if (sysMenu.getMenuId() != null) {
            queryWrapper.ne(SysMenu::getMenuId, sysMenu.getMenuId());
        }
        queryWrapper.last("limit 1");
        return menuMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public boolean hasChildByMenuId(Long menuId) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, menuId);
        return menuMapper.selectCount(queryWrapper) > 0;
    }

    private List<MenuTree> createMenuTree(List<SysMenu> list, long parentId) {
        List<MenuTree> returnList = new ArrayList<>();
        list.forEach(sysMenu -> {
            if (parentId == sysMenu.getParentId()) {
                MenuTree menuTree = new MenuTree();
                menuTree.setMenu(sysMenu);
                menuTree.setChildren(createMenuTree(list, sysMenu.getMenuId()));
                returnList.add(menuTree);
            }
        });
        return returnList;
    }


    private List<ElTree> createElTree(List<SysMenu> list, long parentId) {
        List<ElTree> returnList = new ArrayList<>();
        list.forEach(sysMenu -> {
            if (parentId == sysMenu.getParentId()) {
                ElTree elTree = new ElTree();
                elTree.setId(String.valueOf(sysMenu.getMenuId()));
                elTree.setParentId(String.valueOf(sysMenu.getParentId()));
                elTree.setLabel(sysMenu.getMenuName());
                elTree.setChildren(createElTree(list, sysMenu.getMenuId()));
                returnList.add(elTree);
            }
        });
        return returnList;
    }
}
