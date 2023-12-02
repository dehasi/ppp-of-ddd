package ch15.persistence;

import ch15.name.NameWithPersistence.Name;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Persistence_format_examples {

    @Test void Each_value_has_a_unique_representation() {
        var sallySmith = new Name("Sally", "Smith");
        assertThat("firstName:Sally;;surname:Smith").isEqualTo(sallySmith.toString());

        var billyJean = new Name("Billy", "Jean");
        assertThat("firstName:Billy;;surname:Jean").isEqualTo(billyJean.toString());
    }
}
