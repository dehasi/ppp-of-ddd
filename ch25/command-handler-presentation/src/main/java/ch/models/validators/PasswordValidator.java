package ch.models.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        // Validate password rules
        return password != null
                && password.length() > 1;
    }
}
