package com.you.common.core.utils.JsonSerializer.bigDecimal;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON序列化BigDecimal 保留小数点位数
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = BigDecimalSerializer.class)
public @interface JsonDecimal {

    /**
     * 小数点位数
     * 默认2位
     */
    int scale() default 2;
}
