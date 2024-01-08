package ch.application.handlers;

import ch.application.commands.AddProductToBasketCommand;
import ch.application.readModel.ProductRepository;
import ch.model.baskets.IBasketPricingService;
import ch.model.baskets.IBasketRepository;

public class AddProductToBasketCommandHandler implements ICommandHandler<AddProductToBasketCommand> {

    private final IBasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final IBasketPricingService basketPricingService;

    public AddProductToBasketCommandHandler(IBasketRepository basketRepository,
                                            ProductRepository productRepository,
                                            IBasketPricingService basket_pricing_service) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        basketPricingService = basket_pricing_service;
    }

    @Override public void action(AddProductToBasketCommand businessRequest) {
        var basket = basketRepository.find_by(businessRequest.basket_id());

        var product = productRepository.find_by(businessRequest.product_id());

        basket.add(product.snapshot(), basketPricingService);
    }
}
