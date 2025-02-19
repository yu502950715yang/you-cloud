package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.domain.model.SysNotice;
import com.you.system.domain.qo.NoticeQo;
import com.you.system.mapper.SysNoticeMapper;
import com.you.system.service.SysNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    private final SysNoticeMapper noticeMapper;

    @Override
    public IPage<SysNotice> listPage(NoticeQo qo) {
        return noticeMapper.listPage(qo.getPage(), qo);
    }
}
