package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

    SysUser selectLoginUserByUserId(String userId);
}
