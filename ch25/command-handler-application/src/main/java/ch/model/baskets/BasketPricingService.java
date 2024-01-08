package ch.model.baskets;

import ch.model.Money;
import ch.model.PromotionsNamespace;
import ch.model.PromotionsNamespace.IPromotionsRepository;

import java.util.List;

import static ch.model.PromotionsNamespace.CouponIssues.NotApplied;

public class BasketPricingService implements IBasketPricingService {

    private final IBasketRepository _basketRepository;
    private final IPromotionsRepository _promotions_repository;

    public BasketPricingService(IBasketRepository basketRepository, IPromotionsRepository promotions_repository) {
        _basketRepository = basketRepository;
        _promotions_repository = promotions_repository;
    }

    // Side effect free function
    @Override public BasketPricingBreakdown calculate_total_price_for(List<BasketItem> items, List<PromotionsNamespace.Coupon> coupons) {
        var basketPricingBreakdown = new BasketPricingBreakdown();
        var basket_discount = new Money(0);
        var coupon_issue = NotApplied;

        //foreach (var coupon in coupons)
        //{
        //    // 1. Get all coupons associted with the basket
        //    // 2. Check if it is applicable or which one wins.
        //    var promotion = _promotions_repository.find_by(coupon.code);

        //    if (promotion.is_still_active() && promotion.can_be_applied_to(basket))
        //    {
        //        basket_discount = promotion.calculate_discount_for(items);
        //        coupon_issue = CouponIssues.NoIssue;
        //    }
        //    else
        //    {
        //        coupon_issue = coupon.reason_why_cannot_be_applied_to(items);
        //    }
        //}

        var total = new Money(0);
        for (var item : items) {
            total = total.add(item.line_total());
        }

        basketPricingBreakdown.basket_total = total;
        basketPricingBreakdown.discount = basket_discount;
        basketPricingBreakdown.coupon_message = coupon_issue;

        return basketPricingBreakdown;
    }
}
