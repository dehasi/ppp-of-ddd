package ch12.listing8.domain;

import java.util.List;

public record BasketDTO(int deliveryOptionId, List<Item> items) {
    public static final class Item {}
}
