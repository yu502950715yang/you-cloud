package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.you.system.domain.model.SysNotice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知公告mapper
 */
@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
}
