package com.you.system.domain.qo;

import com.you.common.core.domain.BaseQo;
import com.you.system.domain.model.SysPost;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位查询实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostQo extends BaseQo<SysPost> {

    private String postCode;

    private String postName;

    private String status;
}
