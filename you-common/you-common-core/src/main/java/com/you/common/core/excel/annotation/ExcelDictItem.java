package com.you.common.core.excel.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel导出字典转换注解
 * 将excel导出的字典code自动转换为字典label
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
//@JsonSerialize(using = DictItemSerialize.class)
public @interface ExcelDictItem {

    /**
     * 字典type
     */
    String type();
}
