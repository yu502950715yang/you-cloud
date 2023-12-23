package com.you.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.model.SysMenu;
import com.you.system.qo.MenuQo;
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

    /**
     * 获取所有菜单
     *
     * @param qo 查询条件
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(MenuQo qo);

    /**
     * 校验菜单名称是否存在
     *
     * @param sysMenu 菜单参数
     * @return 结果
     */
    boolean checkMenuNameUnique(SysMenu sysMenu);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean hasChildByMenuId(Long menuId);
}
