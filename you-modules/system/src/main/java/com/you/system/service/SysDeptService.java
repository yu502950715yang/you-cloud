package com.you.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.domain.model.SysDept;
import com.you.system.domain.qo.DeptQo;
import com.you.system.domain.vo.ElTree;

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

    /**
     * 是否有子部门
     *
     * @param deptId 部门id
     * @return 结果
     */
    boolean hasChildByDeptId(Long deptId);

    /**
     * 是否存在人员
     *
     * @param deptId 部门id
     * @return 结果
     */
    boolean hasUserByDeptId(Long deptId);

    /**
     * 根据部门id删除
     *
     * @param deptId 部门id
     * @return 结果
     */
    boolean delById(Long deptId);

    /**
     * 获取部门树前台ElTree展示数据
     *
     * @return 部门树list
     */
    List<ElTree> selectDeptList();

    /**
     * 获取所有子部门
     *
     * @param deptId 部门id
     * @return 子部门数据
     */
    List<SysDept> getAllChildByDeptId(Long deptId);
}
