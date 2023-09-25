package com.you.system.service;

import java.util.List;

public interface SysRoleMenuService {

    /**
     * 批量添加
     *
     * @param roleId     角色id
     * @param menuIdList 菜单id list集合
     */
    void batchSave(long roleId, List<Long> menuIdList);

    /**
     * 根据角色id获取对应的菜单id集合
     *
     * @param roleId 角色id
     * @return 菜单id集合
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色id删除管理信息
     *
     * @param roleId 角色id
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据角色id list 批量删除关联
     *
     * @param roleIds 角色id List
     */
    void deleteByRoleIds(List<Long> roleIds);
}
