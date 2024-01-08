package ch.application.handlers;

import ch.application.commands.RemoveProductFromBasketCommand;
import ch.application.readModel.ProductRepository;
import ch.model.baskets.IBasketPricingService;
import ch.model.baskets.IBasketRepository;

public class RemoveProductFromBasketCommandHandler implements ICommandHandler<RemoveProductFromBasketCommand> {

    private final IBasketRepository _basketRepository;
    private final IBasketPricingService _basketPricingService;
    private final ProductRepository productRepository;

    public RemoveProductFromBasketCommandHandler(IBasketRepository basket_repository,
                                                 IBasketPricingService basket_pricing_service, ProductRepository productRepository) {
        _basketRepository = basket_repository;
        _basketPricingService = basket_pricing_service;
        this.productRepository = productRepository;
    }


    @Override public void action(RemoveProductFromBasketCommand businessRequest) {
        var basket = _basketRepository.find_by(businessRequest.basket_id());

        var productSnapshot = productRepository.find_by(businessRequest.product_id()).snapshot();
        basket.remove_product_with_id_of(productSnapshot, _basketPricingService);
    }
}
