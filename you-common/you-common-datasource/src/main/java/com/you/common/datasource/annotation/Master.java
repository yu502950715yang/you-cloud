package com.you.common.datasource.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * 主数据源注解
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-11 15:10
 */
// 作用范围 type：接口、类、枚举、注解；method：方法
@Target({ElementType.TYPE, ElementType.METHOD,})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("master")
public @interface Master {
}
