package ch14.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class SterlingCurrency extends Currency {
    @Override public String formatForDisplaying(BigDecimal value) {
        return String.format("%s pound", value.setScale(2, RoundingMode.FLOOR));
    }
}
