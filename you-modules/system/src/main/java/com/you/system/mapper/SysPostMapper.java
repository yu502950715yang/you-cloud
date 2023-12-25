package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.model.SysPost;
import com.you.system.qo.PostQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysPostMapper extends BaseMapper<SysPost> {

    IPage<SysPost> listPage(@Param("page") Page<SysPost> page, @Param("postQo") PostQo postQo);
}
