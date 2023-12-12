package com.you.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.enums.DelFlagEnum;
import com.you.common.core.enums.StatusEnum;
import com.you.common.core.exception.CommonException;
import com.you.system.mapper.SysDeptMapper;
import com.you.system.model.SysDept;
import com.you.system.qo.DeptQo;
import com.you.system.service.SysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper deptMapper;

    public SysDeptServiceImpl(SysDeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    @Override
    public List<SysDept> list(DeptQo deptQo) {
        return deptMapper.selectList(deptQo);
    }

    @Override
    public boolean addDept(SysDept sysDept) {
        SysDept parentDept = deptMapper.selectById(sysDept.getParentId());
        if (parentDept == null) {
            throw new CommonException(sysDept.getDeptName() + "父部门不存在");
        }
        if (StatusEnum.DISABLE.getCode().equals(parentDept.getStatus())) {
            throw new CommonException(parentDept.getDeptName() + "已禁用，无法新增子部门");
        }
        sysDept.setAncestors(parentDept.getAncestors() + "," + parentDept.getDeptId());
        sysDept.setCreateTime(LocalDateTime.now());
        sysDept.setCreateBy(LoginUtils.getLoginUserName());
        return deptMapper.insert(sysDept) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean editDept(SysDept sysDept) {
        SysDept newParentDept = deptMapper.selectById(sysDept.getParentId());
        if (newParentDept == null) {
            throw new CommonException(sysDept.getDeptName() + "父部门不存在");
        }
        SysDept oldDept = deptMapper.selectById(sysDept.getDeptId());
        if (oldDept == null) {
            throw new CommonException(sysDept.getDeptName() + "部门不存在");
        }
        // 修改所有子部门节点
        String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
        String oldAncestors = oldDept.getAncestors();
        sysDept.setAncestors(newAncestors);
        // 修改子部门祖级列表
        updateDeptChildren(sysDept.getDeptId(), newAncestors, oldAncestors);
        sysDept.setUpdateBy(LoginUtils.getLoginUserName());
        sysDept.setUpdateTime(LocalDateTime.now());
        return deptMapper.updateById(sysDept) > 0;
    }

    @Override
    public void checkDeptNameUnique(SysDept sysDept) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getDeptName, sysDept.getDeptName())
                .eq(SysDept::getParentId, sysDept.getParentId())
                .eq(SysDept::getDelFlag, DelFlagEnum.NORMAL.getCode());
        if (sysDept.getDeptId() != null) {
            queryWrapper.ne(SysDept::getDeptId, sysDept.getDeptId());
        }
        if (deptMapper.selectCount(queryWrapper) > 0) {
            throw new CommonException("部门名称已存在");
        }
    }

    @Override
    public boolean hasChildByDeptId(Long deptId) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getParentId, deptId)
                .eq(SysDept::getDelFlag, DelFlagEnum.NORMAL.getCode());
        return deptMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean hasUserByDeptId(Long deptId) {
        return deptMapper.selectCountDeptUser(deptId) > 0;
    }

    @Override
    public boolean delById(Long deptId) {
        if (deptId == null) {
            return true;
        }
        return deptMapper.deleteByDeptId(deptId) > 0;
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (!children.isEmpty()) {
            deptMapper.updateDeptChildren(children);
        }
    }
}
