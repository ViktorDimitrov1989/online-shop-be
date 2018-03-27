package com.online.shop.areas.articles.annotations;

import com.online.shop.response.ResponseMessageConstants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = IsMinAndMaxAgeCorrectValidator.class)
public @interface IsMinAndMaxAgeCorrect {

    String message() default ResponseMessageConstants.PASSWORDS_MISMATCH;

    Class<?>[] groups() default  {};

    Class<? extends Payload>[] payload() default {};

}
