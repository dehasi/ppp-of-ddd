package ch15.name;

import ch15.ValueObject;
import me.dehasi.replacements.contotion.Check;
import me.dehasi.replacements.exception.ApplicationException;

import java.util.List;

import static zeliba.the.TheString.the;

public class Name extends ValueObject<Name> {

    final String firstName;
    final String surname;

    public Name(String firstName, String surname) {
        Check.that(the(firstName).isNotEmpty()).onConstraintFailure(() -> {
            throw new ApplicationException("You must specify a first name.");
        });

        Check.that(the(surname).isNotEmpty()).onConstraintFailure(() -> {
            throw new ApplicationException("You must specify a surname.");
        });

        this.firstName = firstName;
        this.surname = surname;
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(firstName, surname);
    }
}
