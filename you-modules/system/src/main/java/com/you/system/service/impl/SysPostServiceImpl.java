package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.mapper.SysPostMapper;
import com.you.system.model.SysPost;
import com.you.system.qo.PostQo;
import com.you.system.service.SysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    private final SysPostMapper sysPostMapper;

    @Override
    public IPage<SysPost> listPage(PostQo qo) {
        return sysPostMapper.listPage(qo.getPage(), qo);
    }
}
