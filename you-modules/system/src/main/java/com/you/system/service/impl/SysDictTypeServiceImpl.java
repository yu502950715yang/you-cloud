package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.common.core.constant.CacheConstants;
import com.you.common.redis.service.RedisService;
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
    private final RedisService redisService;

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
        boolean flag = sysDictTypeMapper.updateById(sysDictType) > 0;
        if (flag) {
            String redisKey = redisService.splitRedisKey(CacheConstants.REDIS_SYS_DICT_KEY, sysDictType.getDictType());
            // 清除缓存
            redisService.deleteObject(redisKey);
        }
        return flag;
    }
}
