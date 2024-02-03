package com.you.common.excel.annotation;

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
public @interface ExcelDictItem {

    /**
     * 字典type
     */
    String type();
}
