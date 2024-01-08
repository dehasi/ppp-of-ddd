package ch.application.commands;

import java.util.UUID;

public record ApplyCouponToBasketCommand(
        UUID basket_id,
        String voucher_id
) implements IBusinessRequest {}
