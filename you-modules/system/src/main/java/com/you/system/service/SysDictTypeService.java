package com.you.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.domain.model.SysDictType;
import com.you.system.domain.qo.DictTypeQo;

public interface SysDictTypeService extends IService<SysDictType> {

    IPage<SysDictType> listPage(DictTypeQo qo);
}
