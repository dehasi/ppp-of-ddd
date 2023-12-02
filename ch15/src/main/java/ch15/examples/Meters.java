package ch15.examples;

import java.util.Objects;

public class Meters {

    protected final double distanceInMeters;

    // BigDecimal suits it better, but with double it's less code.
    public Meters(double distanceInMeters) {
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

    public Meters add(Meters meters) {
        return new Meters(this.distanceInMeters + meters.distanceInMeters);
    }

    public boolean isLongerThan(Meters meters) {
        return this.distanceInMeters > meters.distanceInMeters;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meters meters)) return false;
        return Double.compare(meters.distanceInMeters, distanceInMeters) == 0;
    }

    @Override public int hashCode() {
        return Objects.hash(distanceInMeters);
    }

    private static double toTwoDecimalPlaces(double distanceInMeters) {
        return Math.round(distanceInMeters * 100.0d) / 100.0d;
    }

    public static class Yards {
        public Yards(double distanceInYards) {}
    }

    public static class Kilometers {
        public Kilometers(double distanceInKiloMeters) {}
    }

    public static class DistancesInMetersCannotBeNegative extends RuntimeException {}
}
