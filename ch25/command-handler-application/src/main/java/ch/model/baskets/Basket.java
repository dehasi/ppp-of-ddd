package ch.model.baskets;

import ch.infrastructure.DomainEvents;
import ch.model.Money;
import ch.model.PromotionsNamespace.Coupon;
import ch.model.PromotionsNamespace.Promotion;
import ch.model.baskets.events.BasketCreated;
import ch.model.baskets.events.BasketPriceChanged;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Basket {

    public UUID id;
    private List<BasketItem> items;
    private List<Coupon> coupons;

    private Basket() {
    }

    public Basket(UUID id) {
        this.id = id;
        items = new ArrayList<>();

        DomainEvents.raise(new BasketCreated(this.id, new Money(0.)));
    }

    public List<BasketItem> items() {
        // for testitg
        return new ArrayList<>(items);
    }

    public void add(ProductSnapshot product_snapshot, IBasketPricingService basket_pricing_service) {
        // TODO: Check for null values and invalid data

        if (basket_contains_an_item_for(product_snapshot))
            get_item_for(product_snapshot).increase_item_quantity_by(new NonNegativeQuantity(1));
        else
            items.add(BasketItemFactory.create_item_for(product_snapshot));

        recalculate_basket_totals(basket_pricing_service);
    }

    private BasketItem get_item_for(ProductSnapshot product_snapshot) {
        return items.stream().filter(item -> item.contains(product_snapshot)).findFirst().orElse(null);
    }

    private boolean basket_contains_an_item_for(ProductSnapshot product_snapshot) {
        return items.stream().anyMatch(item -> item.contains(product_snapshot));
    }

    public void remove_product_with_id_of(ProductSnapshot product, IBasketPricingService basket_pricing_service) {
        if (basket_contains_an_item_for(product)) {
            items.remove(get_item_for(product));

            recalculate_basket_totals(basket_pricing_service);
        }
    }

    private void recalculate_basket_totals(IBasketPricingService basket_pricing_service) {
        var total = basket_pricing_service.calculate_total_price_for(items, coupons);

        DomainEvents.raise(new BasketPriceChanged(this.id, total.basket_total, total.discount));
    }

    public void change_quantity_of_product(NonNegativeQuantity quantity, ProductSnapshot product_snapshot, IBasketPricingService basket_pricing_service) {
        // TODO: Check for null values and invalid data

        if (basket_contains_an_item_for(product_snapshot)) {
            if (quantity.is_zero()) {
                remove_product_with_id_of(product_snapshot, basket_pricing_service);
            } else
                get_item_for(product_snapshot).change_item_quantity_to(quantity);

            recalculate_basket_totals(basket_pricing_service);
        }
    }

    public boolean contains_product(Predicate<BasketItem> predicate) {
        return items.stream().anyMatch(predicate);
    }

    public void apply(Promotion promotion, IBasketPricingService basket_pricing_service) {
        // double dispatch
        var coupon = promotion.create_coupon_for(this.id);

        coupons.add(coupon);

        recalculate_basket_totals(basket_pricing_service);
    }

    public void remove_coupon(String coupon_code, IBasketPricingService basket_pricing_service) {
        var coupon = coupons.stream().filter(c -> c.code().equals(coupon_code)).findFirst().orElse(null);

        coupons.remove(coupon);

        recalculate_basket_totals(basket_pricing_service);
    }
}
