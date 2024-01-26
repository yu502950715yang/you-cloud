package com.you.system.domain.bo;

import com.you.system.model.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色编辑实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
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
