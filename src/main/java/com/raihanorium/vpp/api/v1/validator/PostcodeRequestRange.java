package com.raihanorium.vpp.api.v1.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GetBatteriesRequestValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PostcodeRequestRange {
    String message() default "{battery.postcode.size}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
