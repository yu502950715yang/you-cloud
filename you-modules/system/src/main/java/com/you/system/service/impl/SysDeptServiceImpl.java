package com.you.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.mapper.SysDeptMapper;
import com.you.system.model.SysDept;
import com.you.system.qo.DeptQo;
import com.you.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper deptMapper;

    public SysDeptServiceImpl(SysDeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    @Override
    public List<SysDept> listPage(DeptQo deptQo) {
        return deptMapper.selectList(deptQo);
    }
}
