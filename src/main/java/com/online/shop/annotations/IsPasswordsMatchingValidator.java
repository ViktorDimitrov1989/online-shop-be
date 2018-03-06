package com.online.shop.annotations;

import com.online.shop.model.binding.user.RegisterUserBindingModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPasswordsMatchingValidator implements ConstraintValidator<IsPasswordsMatching, Object> {

    @Override
    public void initialize(IsPasswordsMatching constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object userClass, ConstraintValidatorContext constraintValidatorContext) {

        if(userClass instanceof RegisterUserBindingModel){
            return ((RegisterUserBindingModel) userClass).getPassword().equals(((RegisterUserBindingModel) userClass).getConfirmPassword());
        }

        return false;
    }
}
