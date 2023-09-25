package com.you.system.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserRoleMapper {

    /**
     * 根据角色id 查询该角色下有多少用户
     *
     * @param roleId 角色id
     * @return 用户数量
     */
    int countUsers(Long roleId);
}
