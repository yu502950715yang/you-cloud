package com.you.system.bo;

import com.you.system.model.SysRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 角色编辑实体
 */
@Getter
@Setter
public class SysRoleBo extends SysRole {

    /**
     * 角色对应的菜单
     */
    private List<Long> menuIds;

    /**
     * 该角色下的人数
     */
    private int userCount;
}
