package com.you.system.domain.qo;

import com.you.common.model.PageModel;
import com.you.system.domain.model.SysPost;
import lombok.Data;

/**
 * 岗位查询实体
 */
@Data
public class PostQo {

    private String postCode;

    private String postName;

    private String status;

    private PageModel<SysPost> page;
}
