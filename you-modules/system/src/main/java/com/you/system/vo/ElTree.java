package com.you.system.vo;

import lombok.Data;

import java.util.List;

/**
 * element plus el-tree实体
 */
@Data
public class ElTree {

    private String id;

    private String parentId;

    private String label;

    private List<ElTree> children;
}
