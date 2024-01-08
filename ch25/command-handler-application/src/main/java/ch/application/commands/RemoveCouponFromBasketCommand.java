package ch.application.commands;

import java.util.UUID;

public record RemoveCouponFromBasketCommand(
        UUID basket_id,
        String couponCode
) implements IBusinessRequest {}
