package com.you.common.datasource.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * 从数据源
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-11 15:45
 */
@Target({ElementType.TYPE, ElementType.METHOD,})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("slave")
public @interface Slave {
}
