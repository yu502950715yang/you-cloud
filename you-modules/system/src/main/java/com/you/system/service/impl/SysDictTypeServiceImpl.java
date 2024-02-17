package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.domain.model.SysDictType;
import com.you.system.domain.qo.DictTypeQo;
import com.you.system.mapper.SysDictTypeMapper;
import com.you.system.service.SysDictDataService;
import com.you.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final SysDictTypeMapper sysDictTypeMapper;
    private final SysDictDataService dictDataService;

    @Override
    public IPage<SysDictType> listPage(DictTypeQo qo) {
        return sysDictTypeMapper.listPage(qo.getPage(), qo);
    }

    @Override
    public boolean checkDictTypeUnique(SysDictType sysDictType) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictType::getDictType, sysDictType.getDictType());
        if (sysDictType.getDictId() != null) {
            queryWrapper.ne(SysDictType::getDictId, sysDictType.getDictId());
        }
        return sysDictTypeMapper.selectCount(queryWrapper) == 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean editDictType(SysDictType sysDictType) {
        SysDictType oldData = sysDictTypeMapper.selectById(sysDictType.getDictId());
        if (!oldData.getDictType().equals(sysDictType.getDictType())) {
            // 修改字典数据表
            dictDataService.updateDictTypeByDictType(oldData.getDictType(), sysDictType.getDictType());
        }
        return sysDictTypeMapper.updateById(sysDictType) > 0;
    }
}
