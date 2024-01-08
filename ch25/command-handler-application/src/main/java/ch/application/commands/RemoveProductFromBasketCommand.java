package ch.application.commands;

import java.util.UUID;

public record RemoveProductFromBasketCommand(
        UUID basket_id,
        int product_id
) implements IBusinessRequest {}
