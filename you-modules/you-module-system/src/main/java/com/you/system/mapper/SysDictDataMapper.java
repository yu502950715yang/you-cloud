package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.api.domain.model.SysDictData;
import com.you.system.domain.qo.DictDataQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    IPage<SysDictData> listPage(@Param("page") Page<SysDictData> page, @Param("qo") DictDataQo qo);
}
