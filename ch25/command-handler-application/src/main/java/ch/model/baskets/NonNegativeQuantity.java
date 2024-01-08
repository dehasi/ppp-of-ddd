package ch.model.baskets;

public record NonNegativeQuantity(int value) {
    public NonNegativeQuantity {
        if (value < 0)
            throw new RuntimeException("Expected value >=0, but got " + value);
    }

    public NonNegativeQuantity add(NonNegativeQuantity quantity) {
        return new NonNegativeQuantity(value + quantity.value());
    }

    public boolean is_zero() {
        return value == 0;
    }
}
