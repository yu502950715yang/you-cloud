package com.you.system.domain.bo;

import com.you.common.core.domain.model.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserBo extends SysUser {

    /**
     * 岗位id list
     */
    private List<Long> postIds;

    /**
     * 角色id list
     */
    private List<Long> roleIds;
}
