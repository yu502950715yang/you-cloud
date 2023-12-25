package com.you.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.model.SysPost;
import com.you.system.qo.PostQo;

public interface SysPostService extends IService<SysPost> {

    /**
     * 分页
     * @param qo
     * @return 分页数据
     */
    IPage<SysPost> listPage(PostQo qo);
}
