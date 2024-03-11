package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.domain.excel.SysDictTypeExcel;
import com.you.system.domain.model.SysDictType;
import com.you.system.domain.qo.DictTypeQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    IPage<SysDictType> listPage(@Param("page") Page<SysDictType> page, @Param("qo") DictTypeQo qo);

    List<SysDictTypeExcel> selectExcelList(@Param("qo") DictTypeQo qo);
}
