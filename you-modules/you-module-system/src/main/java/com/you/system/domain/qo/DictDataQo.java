package com.you.system.domain.qo;

import com.you.common.core.domain.BaseQo;
import com.you.system.api.domain.model.SysDictData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictDataQo extends BaseQo<SysDictData> {

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
}
