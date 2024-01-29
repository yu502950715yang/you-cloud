package com.you.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.domain.model.SysPost;
import com.you.system.domain.qo.PostQo;

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

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位数据
     * @return 结果
     */
    boolean checkPostNameUnique(SysPost post);

    /**
     * 校验岗位code是否唯一
     *
     * @param post 岗位数据
     * @return 结果
     */
    boolean checkPostCodeUnique(SysPost post);

    /**
     * 获取所有状态为正常的岗位
     *
     * @return 岗位list
     */
    List<SysPost> getAllPost();
}
