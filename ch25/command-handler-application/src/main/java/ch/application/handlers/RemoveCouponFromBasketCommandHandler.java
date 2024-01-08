package ch.application.handlers;

import ch.application.commands.RemoveCouponFromBasketCommand;
import ch.model.baskets.IBasketPricingService;
import ch.model.baskets.IBasketRepository;

public class RemoveCouponFromBasketCommandHandler implements ICommandHandler<RemoveCouponFromBasketCommand> {

    private final IBasketRepository basketRepository;
    private final IBasketPricingService basketPricingService;

    public RemoveCouponFromBasketCommandHandler(IBasketRepository basket_repository,
                                                IBasketPricingService basketPricingService) {
        basketRepository = basket_repository;
        this.basketPricingService = basketPricingService;
    }

    @Override public void action(RemoveCouponFromBasketCommand businessRequest) {
        var basket = basketRepository.find_by(businessRequest.basket_id());

        basket.remove_coupon(businessRequest.couponCode(), basketPricingService);

        basketRepository.save(basket);
    }
}
