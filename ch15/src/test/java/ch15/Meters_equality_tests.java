package ch15;

import ch15.MetersWithBaseClass.MetersWithBaseClass;
import ch15.examples.Meters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Meters_equality_tests {

    @Test void Same_distances_are_equal_even_if_different_references() {
        var oneMeter = new Meters(1);
        var oneMeterX = new Meters(1);
        assertThat(oneMeter).isEqualTo(oneMeterX);

        var fiftyPoint25 = new Meters(50.25);
        var fiftyPoint25X = new Meters(50.25);
        assertThat(fiftyPoint25).isEqualTo(fiftyPoint25X);
    }

    @Test void Same_distances_are_equal_even_if_different_references_with_base() {
        var oneMeter = new MetersWithBaseClass(11.22);
        var oneMeterX = new MetersWithBaseClass(11.22);
        assertThat(oneMeter).isEqualTo(oneMeterX);


        var fiftyMeters = new MetersWithBaseClass(50);
        var fiftyMetersX = new MetersWithBaseClass(50);
        assertThat(fiftyMeters).isEqualTo(fiftyMetersX);
    }
}
