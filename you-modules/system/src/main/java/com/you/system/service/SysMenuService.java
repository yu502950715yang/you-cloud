package com.you.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.model.SysMenu;
import com.you.system.vo.ElTree;
import com.you.system.vo.MenuTree;
import com.you.system.vo.RouterVo;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    List<MenuTree> selectMenuTreeByUserId(Long userId);

    List<RouterVo> buildRouter(List<MenuTree> menuTrees);

    /**
     * 获取系统菜单前台ElTree展示数据
     *
     * @param userId 用户id
     * @return 菜单list
     */
    List<ElTree> selectMenuList(Long userId);

}
