package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.model.SysRole;
import com.you.system.qo.RoleQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色 mapper
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 14:06
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage<SysRole> listPage(@Param("page") Page<SysRole> page, @Param("roleQo") RoleQo roleQo);

    /**
     * 根据id更新角色状态
     *
     * @param roleId 角色id
     * @param status 角色状态
     * @return 执行行数
     */
    int updateState(@Param("roleId") Long roleId, @Param("status") String status);
}
