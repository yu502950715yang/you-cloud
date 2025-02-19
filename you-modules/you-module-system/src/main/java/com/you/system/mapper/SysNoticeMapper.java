package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.domain.model.SysNotice;
import com.you.system.domain.qo.NoticeQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 通知公告mapper
 */
@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    IPage<SysNotice> listPage(@Param("page") Page<SysNotice> page, @Param("qo") NoticeQo qo);
}
