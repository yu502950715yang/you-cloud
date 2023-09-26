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
}
