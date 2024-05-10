package com.you.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
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
        if (userId == null || CollectionUtil.isEmpty(postIds)) {
            return false;
        }
        return userPostMapper.insertUserPosts(userId, postIds) == postIds.size();
    }

    @Override
    public void removeByUserIds(List<Long> userIds) {
        if (CollectionUtil.isNotEmpty(userIds)) {
            userPostMapper.deleteByUserIds(userIds);
        }
    }

    @Override
    public List<Long> getPostIdsByUserId(Long userId) {
        return userPostMapper.selectPostIdsByUserId(userId);
    }
}
