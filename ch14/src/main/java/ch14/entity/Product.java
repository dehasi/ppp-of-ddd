package ch14.entity;

import ch14.valueobject.Option;
import ch14.valueobject.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.math.BigDecimal.ZERO;

public class Product extends Entity<UUID> {

    private final List<Option> options;
    private Price sellingPrice;
    private Price retailPrice;

    public Product(UUID id, Price sellingPrice, Price retailPrice) {
        super(id);
        this.sellingPrice = sellingPrice;
        if (!sellingPriceMatches(retailPrice))
            throw new PricesNotInTheSameCurrencyException(
                    "Selling and retail price must be in the same currency");

        this.retailPrice = retailPrice;
        this.options = new ArrayList<>();
    }

    public void changePriceTo(Price newPrice) {
        if (!sellingPriceMatches(newPrice))
            throw new PricesNotInTheSameCurrencyException(
                    "You cannot change the price of this product to a different currency");
        sellingPrice = newPrice;
    }

    public Price savings() {
        Price savings = retailPrice.minus(sellingPrice);
        if (savings.isGreaterThanZero())
            return savings;
        else
            return new Price(ZERO, sellingPrice.currency());
    }

    public void add(Option option) {
        if (!this.contains(option))
            options.add(option);
        else
            throw new ProductOptionAddedNotUniqueException(
                    String.format(

                            "This product already has the option {%s}",
                            option.toString()));
    }

    public boolean contains(Option option) {
        return options.contains(option);
    }

    private boolean sellingPriceMatches(Price retailPrice) {
        return sellingPrice.equals(retailPrice);
    }

    private static class PricesNotInTheSameCurrencyException extends RuntimeException {
        public PricesNotInTheSameCurrencyException(String message) {super(message);}
    }

    private class ProductOptionAddedNotUniqueException extends RuntimeException {
        public ProductOptionAddedNotUniqueException(String message) {super(message);}
    }

}
