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
    List<SysDept> list(DeptQo deptQo);

    /**
     * 新增部门
     *
     * @param sysDept 部门参数
     * @return 是否成功
     */
    boolean addDept(SysDept sysDept);

    /**
     * 编辑部门
     *
     * @param sysDept 部门参数
     * @return 是否成功
     */
    boolean editDept(SysDept sysDept);

    /**
     * 校验部门名称唯一
     *
     * @param sysDept 部门参数
     */
    void checkDeptNameUnique(SysDept sysDept);

}
