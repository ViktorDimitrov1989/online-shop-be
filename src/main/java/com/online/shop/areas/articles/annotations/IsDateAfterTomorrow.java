package com.online.shop.areas.articles.annotations;

import com.online.shop.response.ResponseMessageConstants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IsDateAfterTomorrowValidator.class)
public @interface IsDateAfterTomorrow {

    String message() default ResponseMessageConstants.INVALID_STATUS_END_DATE;

    Class<?>[] groups() default  {};

    Class<? extends Payload>[] payload() default {};


}
