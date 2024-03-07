package com.you.system.domain.qo;

import com.you.common.model.PageModel;
import com.you.system.domain.model.SysDictType;
import lombok.Data;

@Data
public class DictTypeQo {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态
     */
    private String status;

    private String beginTime;

    private String endTime;

    private PageModel<SysDictType> page;
}
