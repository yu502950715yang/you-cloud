package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.domain.model.SysPost;
import com.you.system.domain.qo.PostQo;
import com.you.system.mapper.SysPostMapper;
import com.you.system.service.SysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    private final SysPostMapper sysPostMapper;

    @Override
    public IPage<SysPost> listPage(PostQo qo) {
        return sysPostMapper.listPage(qo.getPage(), qo);
    }

    @Override
    public List<SysPost> selectList(PostQo qo) {
        return sysPostMapper.selectForList(qo);
    }

    @Override
    public boolean checkPostNameUnique(SysPost post) {
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPost::getPostName, post.getPostName());
        if (post.getPostId() != null) {
            queryWrapper.ne(SysPost::getPostId, post.getPostId());
        }
        return sysPostMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public boolean checkPostCodeUnique(SysPost post) {
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPost::getPostCode, post.getPostCode());
        if (post.getPostId() != null) {
            queryWrapper.ne(SysPost::getPostId, post.getPostId());
        }
        return sysPostMapper.selectCount(queryWrapper) == 0;
    }
}
