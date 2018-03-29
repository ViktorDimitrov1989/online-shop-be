package com.online.shop.areas.articles.annotations;
import com.online.shop.areas.articles.models.binding.CreateCategoryBindingModel;
import com.online.shop.areas.users.models.binding.user.RegisterUserBindingModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMinAndMaxAgeCorrectValidator implements ConstraintValidator<IsMinAndMaxAgeCorrect, Object> {

    @Override
    public void initialize(IsMinAndMaxAgeCorrect constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object categoryClass, ConstraintValidatorContext constraintValidatorContext) {

        if(categoryClass instanceof CreateCategoryBindingModel){
            return ((CreateCategoryBindingModel) categoryClass).getMinAge() < ((CreateCategoryBindingModel) categoryClass).getMaxAge();
        }

        return false;
    }
}
