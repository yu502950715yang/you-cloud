package com.you.common.log.annotation;

import com.you.common.log.enums.OperLogTypEnum;
import com.you.common.log.enums.OperatorTypeEnum;

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

    OperLogTypEnum type();

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    OperatorTypeEnum operatorType() default OperatorTypeEnum.WEB;
}
