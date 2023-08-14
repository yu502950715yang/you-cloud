package com.you.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.common.core.constant.UserConstants;
import com.you.system.mapper.SysMenuMapper;
import com.you.system.model.SysMenu;
import com.you.system.service.SysMenuService;
import com.you.system.utils.RouterUtil;
import com.you.system.vo.MenuTree;
import com.you.system.vo.MetaVo;
import com.you.system.vo.RouterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper menuMapper;

    public SysMenuServiceImpl(SysMenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<MenuTree> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> sysMenuList = menuMapper.selectMenuByUserId(userId);
        return createMenuTree(sysMenuList, 0);
    }

    @Override
    public List<RouterVo> buildRouter(List<MenuTree> menuTrees) {
        List<RouterVo> routerVoList = new ArrayList<>();
        menuTrees.forEach(menuTree -> {
            SysMenu menu = menuTree.getMenu();
            RouterVo router = new RouterVo();
            router.setHidden(1 == menu.getVisible());
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
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
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

}
