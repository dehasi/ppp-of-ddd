package ch.application.handlers;

import ch.application.commands.ApplyCouponToBasketCommand;
import ch.model.PromotionsNamespace.IPromotionsRepository;
import ch.model.baskets.IBasketPricingService;
import ch.model.baskets.IBasketRepository;

public class ApplyCouponToBasketCommandHandler implements ICommandHandler<ApplyCouponToBasketCommand> {

    private final IPromotionsRepository promotionsRepository;
    private final IBasketRepository basketRepository;
    private final IBasketPricingService basketPricingService;


    public ApplyCouponToBasketCommandHandler(IPromotionsRepository promotions_repository,
                                             IBasketRepository basket_repository,
                                             IBasketPricingService basketPricingService) {
        promotionsRepository = promotions_repository;
        basketRepository = basket_repository;
        this.basketPricingService = basketPricingService;
    }

    @Override public void action(ApplyCouponToBasketCommand businessRequest) {
        var promotion = promotionsRepository.find_by(businessRequest.voucher_id());
        // 1. Ensure coupon is valid, still in date
        // Throw Exception - Promo code is invalid

        // Throw Exception - Promo code is not applicable
        var basket = basketRepository.find_by(businessRequest.basket_id());

        basket.apply(promotion, basketPricingService);

        // THROW EXCEPTION IF Coupon is not valid

        basketRepository.save(basket);
    }
}
