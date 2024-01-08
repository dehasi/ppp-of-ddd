package ch.model.baskets;

import ch.model.PromotionsNamespace;

import java.util.List;

public interface IBasketPricingService {
    BasketPricingBreakdown calculate_total_price_for(List<BasketItem> items, List<PromotionsNamespace.Coupon> coupons);
}
