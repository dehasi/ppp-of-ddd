package ch.models.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;

class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private List<String> fields = emptyList();

    @Override public void initialize(FieldsMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        fields = Arrays.asList(constraintAnnotation.fields());
    }

    @Override public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        Set<String> fieldValues = new HashSet<>();
        for (String name : fields) {
            try {
                final Field field = object.getClass().getDeclaredField(name);
                field.setAccessible(true);
                String value = (String) field.get(object);
                fieldValues.add(value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return false;
            }
        }

        return fieldValues.size() == 1;
    }
}
