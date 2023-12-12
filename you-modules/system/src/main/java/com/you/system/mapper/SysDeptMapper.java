package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.you.system.model.SysDept;
import com.you.system.qo.DeptQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    List<SysDept> selectList(@Param("qo") DeptQo qo);

    /**
     * 获取所有子部门
     *
     * @param deptId 部门id
     * @return 子部门集合
     */
    List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 修改子元素关系
     *
     * @param deptList 子元素
     * @return 结果
     */
    int updateDeptChildren(@Param("deptList") List<SysDept> deptList);

    /**
     * 查询部门下有多少用户
     *
     * @param deptId 部门id
     * @return 人数
     */
    int selectCountDeptUser(@Param("deptId") Long deptId);

    /**
     * 逻辑删除
     *
     * @param deptId 部门id
     * @return 行数
     */
    int deleteByDeptId(@Param("deptId") Long deptId);
}
