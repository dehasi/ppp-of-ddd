package ch16.hotel;

class Money {
    // In production, please ise BigDecimal
    final double value;

    Money() {
        this(0d);
    }

    Money(double value) {
        this.value = value;
    }
}
