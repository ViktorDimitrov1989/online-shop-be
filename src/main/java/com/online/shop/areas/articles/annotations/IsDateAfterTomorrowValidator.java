package com.online.shop.areas.articles.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class IsDateAfterTomorrowValidator implements ConstraintValidator<IsDateAfterTomorrow, Object> {

    @Override
    public void initialize(IsDateAfterTomorrow constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object date, ConstraintValidatorContext constraintValidatorContext) {
        Date today = new Date();
        Date chosenDate = (Date) date;

        return chosenDate.after(today);
    }

}
