package com.angkorchat.emoji.cms.global.util.annotation;

import com.angkorchat.emoji.cms.global.util.validator.DateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateValidator.class)
public @interface ValidDate {

    String message() default "Invalid TimeStamp Input";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean afterNow() default false;
}