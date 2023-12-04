package ch16.dice.SideEffectFree;

import java.util.Random;
import java.util.UUID;

class Dice {
    private Random r = new Random();


    private final UUID Id;

    // Good: does not change each time called
    private int value;

    public Dice(UUID id) {
        this.Id = id;
    }

    public UUID getId() {
        return Id;
    }

    public int getValue() {
        return value;
    }

    // Good: sounds like a command - side-effect expected
    public void Roll() {
        value = r.nextInt(1, 7);
    }
}
