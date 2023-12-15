package ch12.listing5;

class WithListItemFactory {
    static WithListItem createFrom(Item item) {
        return new WithListItem();
    }
}
