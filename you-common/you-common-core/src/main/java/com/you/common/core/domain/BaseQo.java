package com.you.common.core.domain;

import com.you.common.model.PageModel;
import lombok.Data;

@Data
public class BaseQo<T> {

    /**
     * 分页
     */
    private PageModel<T> page;
}
