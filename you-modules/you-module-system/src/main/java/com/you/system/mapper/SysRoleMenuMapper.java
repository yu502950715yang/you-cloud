package com.you.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {

    /**
     * 批量新增角色菜单关联
     *
     * @param roleId     角色id
     * @param menuIdList 菜单id list
     */
    void batchInsert(@Param("roleId") Long roleId, @Param("menuIdList") List<Long> menuIdList);

    /**
     * 根据角色id查询菜单id集合
     *
     * @param roleId 角色id
     * @return 菜单id集合
     */
    List<Long> selectMenuIds(Long roleId);

    /**
     * 根据角色id删除关联信息
     *
     * @param roleId 角色id
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据角色id list删除关联信息
     *
     * @param roleIds 角色id List
     */
    void deleteByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * 查询菜单id在角色中的数量
     * @param menuId 菜单id
     * @return 数量
     */
    int selectCountByMenuId(Long menuId);
}
