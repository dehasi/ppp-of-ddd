package ch15;

import ch15.name.Name;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Name_validation_tests {

    @Test void first_names_cannot_be_empty() {
        assertThatThrownBy(() -> {
            var name = new Name("", "Torvalds");
        }).hasMessage("You must specify a first name.");
    }

    @Test void surnames_cannot_be_empty() {
        assertThatThrownBy(() -> {
            var name = new Name("Linus", "");
        }).hasMessage("You must specify a surname.");
    }
}
