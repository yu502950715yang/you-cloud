package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.common.core.constant.CacheConstants;
import com.you.common.core.constant.UserConstants;
import com.you.common.core.utils.text.Convert;
import com.you.common.redis.service.RedisService;
import com.you.system.mapper.SysDictDataMapper;
import com.you.system.model.SysDictData;
import com.you.system.service.SysDictDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    private final RedisService redisService;
    private final SysDictDataMapper dictDataMapper;

    public SysDictDataServiceImpl(RedisService redisService, SysDictDataMapper dictDataMapper) {
        this.redisService = redisService;
        this.dictDataMapper = dictDataMapper;
    }

    @Override
    public List<SysDictData> getDictDataByType(String dictType) {
        String redisKey = redisService.splitRedisKey(CacheConstants.REDIS_SYS_DICT_KEY, dictType);
        if (redisService.hasKey(redisKey)) {
            Object redisList = redisService.getCacheObject(redisKey);
            return Convert.objectToList(redisList, SysDictData.class);
        }
        QueryWrapper<SysDictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysDictData::getDictType, dictType)
                .eq(SysDictData::getStatus, UserConstants.DICT_NORMAL)
                .orderByAsc(SysDictData::getDictSort);
        List<SysDictData> dictDataList = dictDataMapper.selectList(queryWrapper);
        if (!dictDataList.isEmpty()) {
            redisService.setCacheObject(redisKey, dictDataList);
        }
        return dictDataList;
    }
}
