package ch.application.handlers;


import ch.application.commands.CreateABasketCommand;
import ch.model.baskets.Basket;
import ch.model.baskets.IBasketRepository;

public class CreateABasketCommandHandler implements ICommandHandler<CreateABasketCommand> {

    private final IBasketRepository basketRepository;

    public CreateABasketCommandHandler(IBasketRepository basket_repository) {
        basketRepository = basket_repository;
    }

    @Override public void action(CreateABasketCommand businessRequest) {
        var basket = new Basket(businessRequest.basket_id());

        basketRepository.add(basket);
    }
}
