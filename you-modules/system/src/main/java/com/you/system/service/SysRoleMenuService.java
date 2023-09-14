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
}
