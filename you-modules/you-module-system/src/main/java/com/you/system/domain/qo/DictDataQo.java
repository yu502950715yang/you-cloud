package com.you.system.domain.qo;

import com.you.common.model.PageModel;
import com.you.system.api.domain.model.SysDictData;
import lombok.Data;

@Data
public class DictDataQo {

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 状态
     */
    private String status;

    private PageModel<SysDictData> page;
}
