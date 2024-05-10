package com.you.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.enums.DelFlagEnum;
import com.you.common.core.enums.StatusEnum;
import com.you.common.core.exception.CommonException;
import com.you.system.domain.model.SysDept;
import com.you.system.domain.qo.DeptQo;
import com.you.system.domain.vo.ElTree;
import com.you.system.mapper.SysDeptMapper;
import com.you.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper deptMapper;

    @Override
    public List<SysDept> list(DeptQo deptQo) {
        return deptMapper.selectForList(deptQo);
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
        sysDept.setAncestors(parentDept.getAncestors() + parentDept.getDeptId() + ",");
        sysDept.setCreateTime(LocalDateTime.now());
        sysDept.setCreateBy(LoginUtils.getLoginUserName());
        return deptMapper.insert(sysDept) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean editDept(SysDept sysDept) {
        SysDept oldDept = deptMapper.selectById(sysDept.getDeptId());
        if (oldDept == null) {
            throw new CommonException(sysDept.getDeptName() + "部门不存在");
        }
        if (oldDept.getParentId() != 0) {
            SysDept newParentDept = deptMapper.selectById(sysDept.getParentId());
            if (newParentDept == null) {
                throw new CommonException(sysDept.getDeptName() + "父部门不存在");
            }
            // 修改所有子部门节点
            String newAncestors = newParentDept.getAncestors() + newParentDept.getDeptId() + ",";
            String oldAncestors = oldDept.getAncestors();
            sysDept.setAncestors(newAncestors);
            // 修改子部门祖级列表
            updateDeptChildren(sysDept.getDeptId(), newAncestors, oldAncestors);
        }
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

    @Override
    public List<ElTree> selectDeptList() {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getDelFlag, DelFlagEnum.NORMAL.getCode());
        List<SysDept> sysDeptList = deptMapper.selectList(queryWrapper);
        return createElTree(sysDeptList, 0L);
    }

    @Override
    public List<SysDept> getAllChildByDeptId(Long deptId) {
        SysDept parentDept = deptMapper.selectById(deptId);
        if (parentDept == null) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(SysDept::getAncestors, parentDept.getAncestors() + deptId + ",");
        return deptMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysDept> getByDeptIds(Set<Long> deptIds) {
        if (CollectionUtil.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysDept::getDeptId, deptIds);
        return deptMapper.selectList(queryWrapper);
    }

    @Override
    public String getDeptNameByUsername(String username) {
        return null;
    }

    private List<ElTree> createElTree(List<SysDept> list, long parentId) {
        List<ElTree> returnList = new ArrayList<>();
        list.forEach(sysDept -> {
            if (parentId == sysDept.getParentId()) {
                ElTree elTree = new ElTree();
                elTree.setId(String.valueOf(sysDept.getDeptId()));
                elTree.setParentId(String.valueOf(sysDept.getParentId()));
                elTree.setLabel(sysDept.getDeptName());
                elTree.setChildren(createElTree(list, sysDept.getDeptId()));
                returnList.add(elTree);
            }
        });
        return returnList;
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
