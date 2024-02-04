package com.you.system.domain.vo;

import cn.hutool.core.collection.CollUtil;
import com.you.system.api.domain.model.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserVo extends SysUser {

    /**
     * 部门的所有上级
     */
    private String deptAncestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 上级+本部门id
     */
    private List<String> deptIds;

    /**
     * 所有部门名称
     */
    private List<String> deptNames;

    private String deptNamesStr;

    public String getDeptNamesStr() {
        if (CollUtil.isEmpty(deptNames)) {
            return "";
        }
        return String.join("/", deptNames);
    }
}
