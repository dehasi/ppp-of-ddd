package ch.models.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldsMatchValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsMatch {
    String message() default "Field values don't match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fields();
}
