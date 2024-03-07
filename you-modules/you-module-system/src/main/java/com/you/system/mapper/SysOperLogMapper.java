package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.you.system.api.domain.model.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
}
