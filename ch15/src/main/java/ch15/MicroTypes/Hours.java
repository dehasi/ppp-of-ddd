package ch15.MicroTypes;

import ch15.ValueObject;

import java.util.List;

public class Hours extends ValueObject<Hours> {

    public final int amount;

    public Hours(int amount) {
        this.amount = amount;
    }

    public Hours minus(Hours hours) {
        return new Hours(this.amount - hours.amount);
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(amount);
    }


    // Micro Types that wrap existing types for contextual clarity
    public static class HoursWorked extends ValueObject<HoursWorked> {
        public final Hours hours;

        public HoursWorked(Hours hours) {
            this.hours = hours;
        }

        @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
            return List.of(hours);
        }
    }

    public static class ContractedHours extends ValueObject<ContractedHours> {
        public final Hours hours;

        public ContractedHours(Hours hours) {
            this.hours = hours;
        }

        @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
            return List.of(hours);
        }
    }


    public static class OvertimeHours extends ValueObject<OvertimeHours> {
        public final Hours hours;

        public OvertimeHours(Hours hours) {
            this.hours = hours;
        }

        @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
            return List.of(hours);
        }
    }
}
