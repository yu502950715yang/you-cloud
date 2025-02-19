package com.you.system.domain.qo;

import com.you.common.core.domain.BaseQo;
import com.you.system.domain.model.SysDictType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypeQo extends BaseQo<SysDictType> {

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
}
