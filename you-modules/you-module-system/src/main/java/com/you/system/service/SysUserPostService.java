package com.you.system.service;

import java.util.List;

public interface SysUserPostService {

    /**
     * 保存用户与岗位关联信息
     *
     * @param userId  用户id
     * @param postIds 岗位id list
     * @return 是否成功
     */
    boolean saveUserPosts(Long userId, List<Long> postIds);

    /**
     * 删除用户与岗位关联信息
     *
     * @param userIds 用户id list
     */
    void removeByUserIds(List<Long> userIds);

    /**
     * 根据用户id 获取岗位id list
     *
     * @param userId 用户id
     * @return 岗位id list
     */
    List<Long> getPostIdsByUserId(Long userId);
}
