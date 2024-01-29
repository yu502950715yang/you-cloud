package com.you.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {

    /**
     * 根据角色id 查询该角色下有多少用户
     *
     * @param roleId 角色id
     * @return 用户数量
     */
    int countUsers(Long roleId);

    /**
     * 根据用户id删除对应的角色
     *
     * @param roleId  角色id
     * @param userIds 用户id list
     * @return 删除条数
     */
    int deleteRoleByUserIds(@Param("roleId") Long roleId, @Param("userIds") List<Long> userIds);

    /**
     * 根据角色id 获取该角色下的所有用户
     *
     * @param roleId 角色id
     * @return 用户id list
     */
    List<Long> selectUserIdsByRoleId(Long roleId);

    /**
     * 保存用户角色对应关系
     *
     * @param roleId  角色id
     * @param userIds 用户id List
     * @return 保存条数
     */
    int insertUserRole(@Param("roleId") Long roleId, @Param("userIds") List<Long> userIds);

    /**
     * 保存用户角色对应关系
     *
     * @param userId  用户id
     * @param roleIds 角色id list
     * @return 保存条数
     */
    int insertUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}
