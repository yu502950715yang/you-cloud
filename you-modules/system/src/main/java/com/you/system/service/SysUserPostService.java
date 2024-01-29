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
}
