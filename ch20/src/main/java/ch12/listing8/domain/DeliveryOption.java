package ch12.listing8.domain;

import java.util.List;

class DeliveryOption {
    public boolean canBeUsedFor(List<BasketDTO.Item> items) {
        return false;
    }
}
