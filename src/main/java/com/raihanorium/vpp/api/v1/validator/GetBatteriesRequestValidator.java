package com.raihanorium.vpp.api.v1.validator;


import com.raihanorium.vpp.config.AppConfig;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GetBatteriesRequestValidator implements ConstraintValidator<PostcodeRequestRange, List<String>> {

    private int min;
    private int max;

    @Nonnull
    private final AppConfig appConfig;
    @Nonnull
    private final MessageSource messageSource;

    @Override
    public void initialize(PostcodeRequestRange constraintAnnotation) {
        this.min = 1;
        this.max = appConfig.getMaxPostcodesInQuery();
    }

    @Override
    public boolean isValid(List<String> postcodes, ConstraintValidatorContext context) {
        String message = messageSource.getMessage("battery.postcode.size", new Object[]{min, max}, LocaleContextHolder.getLocale());

        if (postcodes == null || postcodes.size() < min || postcodes.size() > max) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
