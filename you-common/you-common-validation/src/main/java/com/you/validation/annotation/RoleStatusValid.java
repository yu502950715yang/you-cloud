package com.you.validation.annotation;

import com.you.validation.constraints.RoleStatusValidConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义校验注解，判断角色状态
 */
@Documented
@Constraint(validatedBy = {RoleStatusValidConstraint.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleStatusValid {

    String message() default "角色状态只能是0或1";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
