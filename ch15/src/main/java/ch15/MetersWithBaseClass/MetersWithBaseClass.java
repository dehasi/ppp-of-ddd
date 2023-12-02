package ch15.MetersWithBaseClass;

import ch15.ValueObject;

import java.util.List;


public class MetersWithBaseClass extends ValueObject<MetersWithBaseClass> {

    private final double distanceInMeters;

    public MetersWithBaseClass(double distanceInMeters) {
        if (distanceInMeters < 0.0d)
            throw new DistancesInMetersCannotBeNegative();

        this.distanceInMeters = distanceInMeters;
    }

    public Yards toYards() {
        return new Yards(distanceInMeters * 1.0936133);
    }

    public Kilometers toKilometers() {
        return new Kilometers(distanceInMeters / 1000);
    }

    public MetersWithBaseClass add(MetersWithBaseClass meters) {
        return new MetersWithBaseClass(this.distanceInMeters + distanceInMeters);
    }

    public boolean isLongerThan(MetersWithBaseClass meters) {
        return this.distanceInMeters > distanceInMeters;
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(distanceInMeters);
    }

    public record Yards(double distanceInYards) {
        // ..
    }

    public record Kilometers(double distanceInKiloMeters) {
        // ..
    }

    public static class DistancesInMetersCannotBeNegative extends RuntimeException {}
}
