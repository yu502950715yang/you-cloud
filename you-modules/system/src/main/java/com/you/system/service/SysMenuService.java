package com.you.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.model.SysMenu;
import com.you.system.vo.MenuTree;
import com.you.system.vo.RouterVo;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    List<MenuTree> selectMenuTreeByUserId(Long userId);

    List<RouterVo> buildRouter(List<MenuTree> menuTrees);
}
