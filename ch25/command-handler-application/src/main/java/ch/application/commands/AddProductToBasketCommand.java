package ch.application.commands;

import java.util.UUID;

public record AddProductToBasketCommand(
        int product_id,
        UUID basket_id
) implements IBusinessRequest {}
