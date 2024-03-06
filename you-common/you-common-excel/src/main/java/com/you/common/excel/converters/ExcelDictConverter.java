package com.you.common.excel.converters;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.you.common.core.constant.CacheConstants;
import com.you.common.core.domain.R;
import com.you.common.core.utils.text.Convert;
import com.you.common.excel.annotation.ExcelDictItem;
import com.you.common.redis.service.RedisService;
import com.you.system.api.domain.model.SysDictData;
import com.you.system.api.feign.RemoteDictService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelDictConverter implements Converter<String> {

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // 获取字典类型
        Field field = contentProperty.getField();
        ExcelDictItem excel = field.getAnnotation(ExcelDictItem.class);
        String dictType = excel.type();

        if (value == null || value.isEmpty()) {
            return new WriteCellData<>("");
        }

        List<SysDictData> sysDictDataList = new ArrayList<>();

        //查询redis缓存
        RedisService redisService = SpringUtil.getBean("redisService");
        String redisKey = redisService.splitRedisKey(CacheConstants.REDIS_SYS_DICT_KEY, dictType);
        if (redisService.hasKey(redisKey)) {
            Object redisList = redisService.getCacheObject(redisKey);
            sysDictDataList = Convert.objectToList(redisList, SysDictData.class);
        } else {
            RemoteDictService remoteDictService = SpringUtil.getBean("remoteDictService");
            R<List<SysDictData>> response = remoteDictService.getDictDataByType(dictType);
            if (Boolean.TRUE.equals(R.isSuccess(response))) {
                sysDictDataList = response.getData();
            }
        }
        if (sysDictDataList.isEmpty()) {
            return new WriteCellData<>("");
        }

        Map<String, String> dictMap = sysDictDataList.stream()
                .collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel, (k1, k2) -> k1));
        String excelData = dictMap.getOrDefault(value, "");
        return new WriteCellData<>(excelData);
    }
}
