package com.you.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.model.SysDept;
import com.you.system.qo.DeptQo;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {


    /**
     * 查询部门列表
     *
     * @param deptQo 查询参数
     * @return 部门列表
     */
    List<SysDept> listPage(DeptQo deptQo);
}
