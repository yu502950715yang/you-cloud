package com.you.common.core.domain;

import com.you.common.model.PageModel;
import lombok.Data;

@Data
public class BaseQo<T> {

    private PageModel<T> page;
}
