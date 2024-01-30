package com.you.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserPostMapper {

    /**
     * 新增用户岗位关联关系
     *
     * @param userId  用户id
     * @param postIds 岗位list
     * @return 保存条数
     */
    int insertUserPosts(@Param("userId") Long userId, @Param("postIds") List<Long> postIds);

    /**
     * 删除用户岗位关联关系
     *
     * @param userIds 用户id list
     * @return 删除条数
     */
    int deleteByUserIds(@Param("userIds") List<Long> userIds);
}
