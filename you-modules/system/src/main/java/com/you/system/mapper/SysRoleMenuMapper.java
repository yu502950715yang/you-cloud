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
}
