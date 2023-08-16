package com.you.validation.constraints;

import com.you.validation.annotation.RoleStatusValid;
import com.you.validation.enums.SysRoleEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleStatusValidConstraint implements ConstraintValidator<RoleStatusValid, String> {
    @Override
    public void initialize(RoleStatusValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return SysRoleEnum.NORMAL.getCode().equals(s) || SysRoleEnum.DISABLE.getCode().equals(s);
    }
}
