package ch15.examples;

import ch15.ValueObject;

import java.util.List;

public class PhoneNumber extends ValueObject<PhoneNumber> {
    final String number;

    PhoneNumber(String number) {this.number = number;}

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(number);
    }
}
