package com.you.common.log.annotation;

import com.you.common.log.enums.OperLogTypenum;

import java.lang.annotation.*;

/**
 * 业务日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OperLog {

    /**
     * 操作模块
     */
    String title();

    OperLogTypenum type();

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    int operatorType() default 1;
}
