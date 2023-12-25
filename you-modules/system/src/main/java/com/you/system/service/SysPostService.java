package com.you.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.model.SysPost;
import com.you.system.qo.PostQo;

import java.util.List;

public interface SysPostService extends IService<SysPost> {

    /**
     * 分页
     *
     * @param qo 查询条件
     * @return 分页数据
     */
    IPage<SysPost> listPage(PostQo qo);

    /**
     * 根据查询条件获取数据
     *
     * @param qo 查询条件
     * @return 岗位数据
     */
    List<SysPost> selectList(PostQo qo);
}
