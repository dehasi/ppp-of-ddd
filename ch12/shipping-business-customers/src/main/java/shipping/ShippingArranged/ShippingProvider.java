package shipping.ShippingArranged;

class ShippingProvider {
    static ShippingConfirmation arrangeShippingFor(String address, String referenceCode) {
        return new ShippingConfirmation(ShippingStatus.Success);
    }
}
