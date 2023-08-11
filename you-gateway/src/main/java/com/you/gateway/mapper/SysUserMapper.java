package com.you.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.you.system.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SysUser mapper
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-12 10:52
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> selectPermTokenByUserId(String userId);
}
