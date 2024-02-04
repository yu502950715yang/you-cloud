package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.domain.model.SysDictType;
import com.you.system.domain.qo.DictTypeQo;
import com.you.system.mapper.SysDictTypeMapper;
import com.you.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final SysDictTypeMapper sysDictTypeMapper;

    @Override
    public IPage<SysDictType> listPage(DictTypeQo qo) {
        return sysDictTypeMapper.listPage(qo.getPage(), qo);
    }
}
