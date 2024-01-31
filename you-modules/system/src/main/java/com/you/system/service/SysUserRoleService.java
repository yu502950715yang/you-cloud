package com.you.system.service;

import java.util.List;

public interface SysUserRoleService {

    /**
     * 根据用户id删除角色
     *
     * @param roleId  角色id
     * @param userIds 用户id list
     * @return 是否成功
     */
    boolean removeRoleByUserIds(Long roleId, List<Long> userIds);

    /**
     * 根据角色id获取该角色下的所有用户id
     *
     * @param roleId 角色id
     * @return 用户id list
     */
    List<Long> getUserIdsByRoleId(Long roleId);

    /**
     * 保存角色用户对应关系
     *
     * @param roleId  角色id
     * @param userIds 用户id List
     * @return 是否成功
     */
    boolean saveUserRole(Long roleId, List<Long> userIds);

    /**
     * 保存用户角色对应关系
     *
     * @param userId  用户id
     * @param roleIds 角色list
     * @return 是否成功
     */
    boolean saveUserRoles(Long userId, List<Long> roleIds);

    /**
     * 根据用户id删除用户角色对应关系
     *
     * @param userIds 用户id list
     */
    void removeByUserIds(List<Long> userIds);

    /**
     * 根据用户id 获取角色id list
     *
     * @param userId 用户id
     * @return 角色id List
     */
    List<Long> getRoleIdsByUserId(Long userId);
}
