package com.online.shop.areas.articles.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

public class IsDateAfterTomorrowValidator implements ConstraintValidator<IsDateAfterTomorrow, Object> {

    @Override
    public void initialize(IsDateAfterTomorrow constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object date, ConstraintValidatorContext constraintValidatorContext) {
        Date today = new Date();
        Date chosenDate = (Date) date;

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(today);
        cal2.setTime(chosenDate);
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

        if(sameDay){
            chosenDate = null;
            return true;
        }

        return chosenDate.after(today);
    }

}
