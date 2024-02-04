package com.you.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.domain.model.SysDictType;
import com.you.system.domain.qo.DictTypeQo;

public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 分页查询
     *
     * @param qo 查询条件
     * @return 分页数据
     */
    IPage<SysDictType> listPage(DictTypeQo qo);

    /**
     * 校验字典类型是否唯一
     *
     * @param sysDictType 字典数据
     * @return true or false
     */
    boolean checkDictTypeUnique(SysDictType sysDictType);
}
