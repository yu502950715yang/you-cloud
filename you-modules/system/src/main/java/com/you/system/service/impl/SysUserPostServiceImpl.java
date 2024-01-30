package com.you.system.service.impl;

import com.you.system.mapper.SysUserPostMapper;
import com.you.system.service.SysUserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SysUserPostServiceImpl implements SysUserPostService {

    private final SysUserPostMapper userPostMapper;

    @Override
    public boolean saveUserPosts(Long userId, List<Long> postIds) {
        if (userId == null || postIds == null || postIds.isEmpty()) {
            return false;
        }
        return userPostMapper.insertUserPosts(userId, postIds) == postIds.size();
    }

    @Override
    public void removeByUserIds(List<Long> userIds) {
        if (userIds != null && !userIds.isEmpty()) {
            userPostMapper.deleteByUserIds(userIds);
        }
    }
}
