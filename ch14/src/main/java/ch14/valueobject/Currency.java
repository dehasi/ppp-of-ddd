package ch14.valueobject;

import java.math.BigDecimal;

public abstract sealed class Currency permits SterlingCurrency {
    public abstract String formatForDisplaying(BigDecimal value);
}
