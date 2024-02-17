package com.you.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.api.domain.model.SysDictData;

import java.util.List;

public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据list
     */
    List<SysDictData> getDictDataByType(String dictType);

    /**
     * 根据字典类型更新字典数据
     *
     * @param oldDictType 旧字典类型
     * @param newDictType 新字典类型
     */
    void updateDictTypeByDictType(String oldDictType, String newDictType);
}
