package ch.application.commands;

import java.util.UUID;

public record CreateABasketCommand(
        UUID basket_id
) implements IBusinessRequest {}
