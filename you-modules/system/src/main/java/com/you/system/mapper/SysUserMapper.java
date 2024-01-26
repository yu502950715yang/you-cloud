package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.domain.qo.AuthUserQo;
import com.you.system.domain.qo.UserQo;
import com.you.system.domain.vo.SysUserVo;
import com.you.system.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SysUser mapper
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-12 10:52
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectByUsername(String username);

    SysUser selectLoginUserByUserId(Long userId);

    IPage<SysUser> ruleAllocatedListPage(@Param("page") Page<SysUser> page, @Param("qo") AuthUserQo qo);

    IPage<SysUser> ruleUnallocatedListPage(@Param("page") Page<SysUser> page, @Param("qo") AuthUserQo qo);

    IPage<SysUserVo> listPage(@Param("page") Page<SysUserVo> page, @Param("qo") UserQo qo);
}
