package com.you.system.mapper;


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
public interface AuthMapper {

    List<String> selectPermTokenByUserId(Long userId);

    List<String> selectAllPerm();

    List<String> selectRolesByUserId(Long userId);


}
