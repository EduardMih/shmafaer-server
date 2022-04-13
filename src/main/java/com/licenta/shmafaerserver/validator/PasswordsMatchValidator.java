package com.licenta.shmafaerserver.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {
    private String password;
    private String confirmPassword;

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext)
    {
        Object passwordValue = new BeanWrapperImpl(o)
                .getPropertyValue(password);
        Object confirmPasswordValue = new BeanWrapperImpl(o)
                .getPropertyValue(confirmPassword);


        if(!Objects.equals(passwordValue, confirmPasswordValue))
        {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Passwords don't match").
                    addPropertyNode(password).addConstraintViolation();

            return false;

        }

        return true;

    }

    @Override
    public void initialize(PasswordsMatch constraintAnnotation)
    {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }
}
