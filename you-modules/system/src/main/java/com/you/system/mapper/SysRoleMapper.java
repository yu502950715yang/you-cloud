package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.you.system.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色 mapper
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 14:06
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id获取权限list
     *
     * @param userId 用户id
     * @return 权限字符串list
     */
    List<String> selectPermTokenByUserId(String userId);
}
