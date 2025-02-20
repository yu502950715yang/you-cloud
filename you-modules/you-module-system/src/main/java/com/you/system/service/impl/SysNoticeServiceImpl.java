package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.auth.utils.LoginUtils;
import com.you.system.domain.model.SysNotice;
import com.you.system.domain.qo.NoticeQo;
import com.you.system.mapper.SysNoticeMapper;
import com.you.system.service.SysNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    private final SysNoticeMapper noticeMapper;

    @Override
    public IPage<SysNotice> listPage(NoticeQo qo) {
        return noticeMapper.listPage(qo.getPage(), qo);
    }

    @Override
    public boolean saveNotice(SysNotice notice) {
        notice.setCreateBy(LoginUtils.getLoginUserName());
        notice.setCreateTime(LocalDateTime.now());
        return noticeMapper.insert(notice) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteNoticeByIds(Long[] noticeIds) {
        if (noticeIds == null || noticeIds.length == 0) {
            return true;
        }
        List<Long> noticeIdList = Arrays.asList(noticeIds);
        return noticeMapper.deleteBatchIds(noticeIdList) == noticeIds.length;
    }

    @Override
    public boolean updateNotice(SysNotice notice) {
        notice.setUpdateBy(LoginUtils.getLoginUserName());
        notice.setUpdateTime(LocalDateTime.now());
        return noticeMapper.updateById(notice) > 0;
    }
}
