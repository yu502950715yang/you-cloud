package com.you.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.api.domain.model.SysOperLog;
import com.you.system.mapper.SysOperLogMapper;
import com.you.system.service.SysOperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {
}
