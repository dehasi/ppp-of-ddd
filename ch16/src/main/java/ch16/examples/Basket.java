package ch16.examples;

import java.util.List;

import static java.util.Objects.requireNonNull;

class Basket {
    // there are no public setters or getters
    private int id;
    private int cost;
    private List<Item> items;

    Basket(int id, int cost, List<Item> items) {
        this.id = id;
        this.cost = cost;
        this.items = requireNonNull(items);
    }

    public void add(Item item) {
        items.add(item);
    }

    public void add(Coupon coupon) {
        cost += coupon.discount();
    }

    // okay for the outside world to be coupled to BasketSnapshot
    public BasketSnapshot GetSnapshot()
    {
        return new BasketSnapshot(this.id, this.cost, this.items.size() /* etc */) ;
    }
    private static class Item {}

    private record Coupon(int discount) {}
}
